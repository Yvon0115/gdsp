package com.gdsp.platform.schedule.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.platform.comm.mail.model.MailInfo;
import com.gdsp.platform.comm.mail.model.MailModel;
import com.gdsp.platform.comm.mail.utils.MailUtils;
import com.gdsp.platform.comm.message.service.IMessageService;
import com.gdsp.platform.comm.message.utils.MessageConst;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.schedule.model.JobParameter;
import com.gdsp.platform.schedule.model.JobReceiver;
import com.gdsp.platform.schedule.utils.ScheduleConst;

/**
 * @ClassName: AbstractJobImpl
 * (这里用一句话描述这个类的作用)
 * @author wwb
 * @date 2015年11月10日 上午11:35:41
 *
 */
public abstract class AbstractJobImpl implements Job {

    protected JobDataMap         data;
    private List<JobParameter>   parameters      = new ArrayList<>();
    protected JobKey             jobKey;
    protected TriggerKey         triggerKey;
    protected String             jobsName;                                                    // triggerDec
    protected String             jobsUser;                                                    // 用户
    protected String             jobsKey;                                                     // triggerName
    /** 业务日志 */
    private static final Logger  schedInfoLogger = LoggerFactory.getLogger("schedInfoLogger");
    //    private static final char[]  String          = null;
    @Autowired
    private IMessageService      messageService;
    @Autowired
    private IUserQueryPubService userQueryPubService;
    @Autowired
    private Scheduler            scheduler;                                                   // 注入调度容器 lijun 20170407

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 执行任务前数据处理
        this.data = context.getTrigger().getJobDataMap();
        Object obj = data.get(ScheduleConst.DATAMAP_KEY_PARA);
        jobKey = context.getJobDetail().getKey();
        triggerKey = context.getTrigger().getKey();
        try {
            parameters = (List<JobParameter>) obj;
        } catch (Exception e) {
            schedInfoLogger.error(e.getMessage(), e);
            throw new BusinessException("参数取数错误！");
        }
        long begintime = System.currentTimeMillis();
        try {
            // 执行任务
            executeJob();
            // 发送消息
            notifyReceivers();
        } catch (Exception e) {
            long endtime = System.currentTimeMillis(); // 精确度更改 wqh 2017/2/6
            if (jobKey.getGroup().equals(ScheduleConst.GROUP_NAME_SUBSCRIBE)) {
                // String subsId=(String)data.get("subsId");//报表ID
                schedInfoLogger.info("busInfoLogger", jobKey.getName(), jobKey.getGroup(), jobsKey, jobKey.getGroup(),
                        begintime / 1000, endtime / 1000, endtime - begintime, ScheduleConst.EXEC_RESULT_FAIL,
                        "执行失败：" + e.getMessage(), jobsUser, jobsName);
            } else {
                // 通过getTriggerName()获取triggerName；lijun 20170407
                schedInfoLogger.info("busInfoLogger", jobKey.getName(), jobKey.getGroup(), getTriggerName(),
                        triggerKey.getGroup(), begintime / 1000, endtime / 1000, endtime - begintime,
                        ScheduleConst.EXEC_RESULT_FAIL, "执行失败：" + e.getMessage(), "", "");
            }
            throw e;
        }
        long endtime = System.currentTimeMillis();
        if (jobKey.getGroup().equals(ScheduleConst.GROUP_NAME_SUBSCRIBE)) {
            schedInfoLogger.info("schedInfoLogger", jobKey.getName(), jobKey.getGroup(), jobsKey, jobKey.getGroup(),
                    begintime / 1000, endtime / 1000, endtime - begintime, ScheduleConst.EXEC_RESULT_SUCC, "执行成功",
                    jobsUser, jobsName);
        } else {
            // 通过getTriggerName()获取triggerName；lijun 20170407
            schedInfoLogger.info("schedInfoLogger", jobKey.getName(), jobKey.getGroup(), getTriggerName(),
                    triggerKey.getGroup(), begintime / 1000, endtime / 1000, endtime - begintime,
                    ScheduleConst.EXEC_RESULT_SUCC, "执行成功", "", "");
        }
    }

    public JobKey getJobKey() {
        return jobKey;
    }

    /**
     * @Description: 返回参数设置信息 @return List<JobParameter> 返回类型      */
    protected List<JobParameter> getJobParameters() {
        return parameters;
    }

    /**
     * @Description: 返回参数值 @param key String @return String 返回
     * 
     *      */
    public String getString(String key) {
        if (parameters == null || parameters.isEmpty()) {
            return null;
        }
        for (JobParameter parameter : parameters) {
            if (parameter.getParaname().equals(key))
                return parameter.getValue();
        }
        return null;
    }

    /**
     * @Title: getMessageSubject (发送消息体标题，可以重写) @return String
     *         发送消息标题      */
    protected String getMessageSubject() {
        return getJobKey().getName() + "运行报告";
    }

    /**
     * @Title: getMessageInf (发送消息体，可以重写) @return String
     *         发送消息内容      */
    protected String getMessageInf() {
        Object obj = data.get(ScheduleConst.DATAMAP_KEY_MESSAGE);
        return (String) obj;
    }

    /**
     * @Description: 返回接受者设置信息 @return List<JobReceiver> 返回类型      */
    protected List<JobReceiver> getJobReceivers() {
        Object obj = data.get(ScheduleConst.DATAMAP_KEY_RECEIVER);
        try {
            return (List<JobReceiver>) obj;
        } catch (Exception e) {
            schedInfoLogger.error(e.getMessage(), e);
            throw new BusinessException("消息接受者获取错误！");
        }
    }

    /**
     * @Description: 任务执行      */
    protected abstract void executeJob() throws BusinessException;

    /**
     * 发送通知给任务配置的用户（邮件 & 消息）
     */
    protected void notifyReceivers() {
        List<JobReceiver> jobReceivers = getJobReceivers();
        if (jobReceivers == null || jobReceivers.isEmpty())
            return;
        for (JobReceiver jobReceiver : jobReceivers) {
            // 发送站内消息
            if (jobReceiver.getSendtype() == ScheduleConst.SENDTYPE_MESSAGE) {
                String receiverids = jobReceiver.getReceiverids();
                if (StringUtils.isNotEmpty(receiverids)) {
                    sendMessage(receiverids);
                }
            }
            // 发送邮件
            else if (jobReceiver.getSendtype() == ScheduleConst.SENDTYPE_MAIL) {
                String receiverids = jobReceiver.getReceiverids();
                if (StringUtils.isNotEmpty(receiverids)) {
                    sendMail(receiverids);
                }
            }
        }
    }

    /**
     * 获取任务的消息/邮件接收者
     * 
     * @Description 根据接受者ID返回详细信息
     * @param receiverids
     *            接受者IDs
     * @return List<UserVO> 接受者信息
     */
    // 权限拆分---------------------------------------2016.12.26
    // Condition condition = new Condition();
    // condition.addExpression("u.id", receiverids, "in");
    // Sorter sort = new Sorter(Direction.ASC, "account");
    // return userService.queryUserListByCondition(condition, sort);
    private List<UserVO> getReceivers(String receiverids) {
        List<UserVO> queryUserList = userQueryPubService.queryUserList(receiverids, null);
        return queryUserList;
    }

    /**
     * @Description 发送站内消息
     * @param receiverids
     *            接受者IDs
     */
    protected void sendMessage(String receiverids) {
        List<UserVO> users = getReceivers(receiverids);
        if (users == null || users.isEmpty()) {
            return;
        }
        for (UserVO user : users) {
            messageService.senderMessage(user.getId(), user.getUsername(), MessageConst.SYSTEM_MESSAGE_ID,
                    MessageConst.SYSTEM_MESSAGE_NAME, getMessageSubject(), getMessageInf());
        }
    }

    /**
     * @Title: getAttachment (邮件附件，可以重写) @return String[]
     *         邮件附件      */
    protected MultipartFile[] getAttachment() {
    	MultipartFile[] str = {};
        return str;
    }

    /**
     * 如果Trigger的分组为DEFAULT,则从Data获取name。 因为此时trigger的name是随机码生成的。
     * 
     * @author lijun
     * @return
     */
    protected String getTriggerName() {
        if (("DEFAULT").equals(this.triggerKey.getGroup()) && data.size() > 0) {
            return (java.lang.String) data.get("triggerName");
        }
        return this.triggerKey.getName();
    }

    /**
     * 发送任务邮件
     * 
     * @Description 发送邮件，根据任务配置的用户，发送邮件到用户邮箱<br>
     *              测试用邮箱： gdsp_portal@163.com 密码：1q2w3e4r 1千无のr
     * @param receiverids
     *            接受者IDs
     * @author wqh
     * @since 2017/03/13
     */
    protected void sendMail(String receiverids) {
        List<UserVO> users = getReceivers(receiverids);
        if (users == null || users.isEmpty()) {
            return;
        }
        List<String> toAddress = new ArrayList<>();
        String email;
        // 获取用户邮件信息
        for (UserVO user : users) {
            email = user.getEmail();
            if (StringUtils.isNotBlank(email))
                toAddress.add(email);
        }
        if (toAddress.isEmpty()) {
            return;
        }
        // 发送邮件
        MailInfo mailInfo = new MailInfo();
        mailInfo.setSubject(getMessageSubject());
        mailInfo.setContent(getMessageInf());
        mailInfo.setAttachment(getAttachment());
        mailInfo.setToAddress(toAddress.toArray(new String[toAddress.size()]));
        MailModel mailModel = new MailModel();
        mailModel.setHost(FileUtils.getFileIO("mail.host",true));
//      mailInfo.setMailServerPort(AppConfig.getInstance().getString("mail.port"));
        mailModel.setPort(FileUtils.getFileIO("mail.port",true));
//      mailInfo.setValidate(Boolean.parseBoolean(AppConfig.getInstance().getString("mail.validate")));
        mailModel.setValidate(Boolean.parseBoolean(FileUtils.getFileIO("mail.smtp.auth",true)));
//      mailInfo.setUserName(AppConfig.getInstance().getString("mail.username"));
        mailModel.setUserName(FileUtils.getFileIO("mail.username",true));
//      mailInfo.setPassword(AppConfig.getInstance().getString("mail.password"));//您的邮箱密码   
        mailModel.setPassword(FileUtils.getFileIO("mail.password",true));//您的邮箱密码   
//      mailInfo.setFromAddress(AppConfig.getInstance().getString("mail.username"));
        mailModel.setUserName(FileUtils.getFileIO("mail.username",true));
        mailModel.setMailInfo(mailInfo);
        
        try {
            MailUtils.senderHtmlMail(mailModel);
        } catch (Exception e) {
            schedInfoLogger.error(e.getMessage(), e);
            throw new BusinessException("发送邮件错误！");
        }
    }
}
