package com.gdsp.platform.log.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.dev.base.utils.data.DataType;
import com.gdsp.dev.base.utils.data.TypeConvert;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.platform.common.web.PageRequestInterceptor;
import com.gdsp.platform.log.model.TempLogContentVO;
import com.gdsp.platform.log.model.TempLogOpVO;
import com.gdsp.platform.log.service.ITempLogOpService;
@Aspect
public class LogAspect implements Ordered {
	
	@Autowired
	private ITempLogOpService tempLogOpService;
	
	private static ThreadLocal<TempLogOpVO> local = new ThreadLocal<TempLogOpVO>();
	
	private int order = Ordered.LOWEST_PRECEDENCE;
	
	private static final Logger opInfoLogger = LoggerFactory.getLogger("opMainInfoLogger");
	
	private static final Logger opContentLogger = LoggerFactory.getLogger("opContentLogger");
	
	private static Logger       logger         = LoggerFactory.getLogger(LogAspect.class);
	
	/**
	 * 日志记录异常写入文件
	 */
	private static Logger logExFile = LoggerFactory.getLogger("logExcepFile");

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order){
		this.order = order;
	}
	
	@Pointcut("@annotation(com.gdsp.platform.log.service.OpLog)")
	public void updatePointCut(){};
	
	@Around(value = "updatePointCut()")
	public Object updateAround(ProceedingJoinPoint jp) throws Throwable{
		//业务执行前时间
		DDateTime beforeTransaction = new DDateTime();
		//生成uuid
		String serviceId = UUIDUtils.getRandomUUID();
		TempLogOpVO tempLogOpVO = new TempLogOpVO();
		tempLogOpVO.setCreateTime(beforeTransaction);
		tempLogOpVO.setServiceId(serviceId);
		//数据放入当前线程
		local.remove();
		local.set(tempLogOpVO);
		Object obj = jp.proceed();
		local.remove();
		//业务执行结束时间
		DDateTime afterTransaction = new DDateTime();
		//查询临时表中日志信息
		List<TempLogOpVO> tempLogOpInfoList = tempLogOpService.queryByServiceIdAndTimeRange(serviceId, beforeTransaction.getMillis()/1000, afterTransaction.getMillis()/1000);
		List<TempLogOpVO> changedInfoList = new ArrayList<TempLogOpVO>();
		List<TempLogContentVO> changedContents = new ArrayList<TempLogContentVO>();
		if(tempLogOpInfoList != null && tempLogOpInfoList.size()>0){
			for(TempLogOpVO tempLogOpInfo : tempLogOpInfoList){
				//根据操作类型判断业务为哪种数据库操作
				boolean success = false;
				if("1".equals(tempLogOpInfo.getType())){
					success = isInsertSuccess(tempLogOpInfo);
				}else if("2".equals(tempLogOpInfo.getType())){
					success = isUpdateSuccess(tempLogOpInfo);
				}else if("3".equals(tempLogOpInfo.getType())){
					success = isDeleteSuccess(tempLogOpInfo);
				}
				if(success){
					changedInfoList.add(tempLogOpInfo);
					changedContents.addAll(tempLogOpInfo.getLogContents());
				}
			}
		}
		List<Object> logInfoArgs = new ArrayList<Object>();
		List<String> opIds = new ArrayList<String>();
		if(changedInfoList != null && changedInfoList.size()>0){
			for(TempLogOpVO tempLogOpInfo : changedInfoList){
				List<Object> arg = new ArrayList<Object>();
				arg.add(tempLogOpInfo.getId());
				arg.add(tempLogOpInfo.getTableName());
				arg.add(tempLogOpInfo.getTableDesc());
				arg.add(tempLogOpInfo.getCreateBy());
				arg.add(tempLogOpInfo.getCreateTime().getMillis()/1000);
				arg.add(tempLogOpInfo.getType());
				logInfoArgs.add(arg);
				opIds.add(tempLogOpInfo.getId());
			}
		}
		List<Object> logContentArgs = new ArrayList<Object>();
		List<String> contentIds = new ArrayList<String>();
		if(changedContents != null && changedContents.size()>0){
			for(TempLogContentVO content : changedContents){
				List<Object> arg = new ArrayList<Object>();
				arg.add(content.getId());
				arg.add(content.getLogId());
				arg.add(content.getColName());
				arg.add(content.getColDesc());
				arg.add(content.getNewData());
				arg.add(content.getOldData());
				logContentArgs.add(arg);
				contentIds.add(content.getId());
			}
		}
		if(opIds.size() > 0){
			try{
				//删除临时表的数据
				tempLogOpService.deleteTempLogOP(opIds.toArray(new String[opIds.size()]));
				//把临时表中的数据写入日志表中
				opInfoLogger.info("opMainInfoLogger", logInfoArgs.toArray(new Object[logInfoArgs.size()]));
			}catch(Exception e){
				logExFile.info(e.getMessage()+"写入日志操作正式表异常");
				logger.debug(e.getMessage(),e);
				return obj;
			}
		}
		if(contentIds.size() > 0){
			try{
				//删除临时表的数据
				tempLogOpService.deleteTempLogContent(contentIds.toArray(new String[contentIds.size()]));
				//把临时表中的数据写入日志表中
				opContentLogger.info("opContentLogger", logContentArgs.toArray(new Object[logContentArgs.size()]));
			}catch(Exception e){
				logExFile.info(e.getMessage()+"写入日志字段操作正式表异常");
				logger.debug(e.getMessage(),e);
				return obj;
			}
		}
		return obj;
	}
	
	private boolean isDeleteSuccess(TempLogOpVO tempLogOpInfo) throws ClassNotFoundException {
		Condition condition = new Condition();
		try{
			condition.addExpression("id", tempLogOpInfo.getDataId());
			Integer count = tempLogOpService.queryByTableName(tempLogOpInfo.getTableName(), condition);
			if(count == 0){
				return true;
			}
		}catch(Exception e){
			logExFile.info(e.getMessage()+"判断数据删除是否成功时出现异常");
			logger.debug(e.getMessage(),e);
			return false;
		}
		return false;
	}

	private boolean isUpdateSuccess(TempLogOpVO tempLogOpInfo) throws ClassNotFoundException {
		List<TempLogContentVO> logContents = tempLogOpInfo.getLogContents();
		Condition condition = null;
		try{
			if(logContents != null && logContents.size() > 0){
				condition = new Condition();
				condition.addExpression("id", tempLogOpInfo.getDataId());
				for(TempLogContentVO content : logContents){
					//DataType dataType = DataType.parse(content.getValueType());
					//Object value = TypeConvert.translate(dataType, content.getNewData());
					if(content.getNewData() != null && !content.getNewData().equals(content.getOldData())
						|| (content.getNewData() == null && content.getOldData() != null)){
						if("aging_enddate".equals(content.getColName())){
							//将时间字符串变为数字
							BigInteger agingTime = new BigInteger(content.getNewData());
							//int agingTime = Integer.parseInt(content.getNewData());
							condition.addExpression(content.getColName(), agingTime);
						}else{
							condition.addExpression(content.getColName(), content.getNewData());
						}
					}
				}
			}
			if(condition != null){
				Integer count = tempLogOpService.queryByTableName(tempLogOpInfo.getTableName(), condition);
				if(count != 0){
					return true;
				}
			}
		}catch(Exception e){
			logExFile.info(e.getMessage()+"判断数据更新是否成功时出现异常");
			logger.debug(e.getMessage(),e);
			return false;
		}
		return false;
	}

	private boolean isInsertSuccess(TempLogOpVO tempLogOpInfo) throws ClassNotFoundException {
		Condition condition = new Condition();
		try{
			condition.addExpression("id", tempLogOpInfo.getDataId());
			Integer count = tempLogOpService.queryByTableName(tempLogOpInfo.getTableName(), condition);
			if(count != 0){
				return true;
			}
		}catch(Exception e){
			logExFile.info(e.getMessage()+"判断数据插入是否成功时出现异常");
			logger.debug(e.getMessage(),e);
			return false;
		}
		return false;
	}

	public static TempLogOpVO getTempLogOpVO(){
		return local.get();
	}
}
