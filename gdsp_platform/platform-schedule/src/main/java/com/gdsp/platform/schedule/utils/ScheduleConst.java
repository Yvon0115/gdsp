package com.gdsp.platform.schedule.utils;

/***
 * 任务调度常量
 * @author pc
 * @since 2017年9月29日 下午2:11:35
 */
public interface ScheduleConst {

    public static String    GROUP_NAME_ALERT        = "alert";     // 预警
    public static String    GROUP_NAME_JOB          = "job";       // 任务
    public static String    GROUP_NAME_SUBSCRIBE    = "subscribe"; // 报表订阅
    public static String    TRIGGER_STATE_EXECUTING = "1";         // 激活
    public static String    TRIGGER_STATE_PAUSED    = "2";         // 休眠
    
    public static String    DATAMAP_KEY_PARA        = "paramter";  // 参数
    public static String    DATAMAP_KEY_MESSAGE     = "message";   // 消息
    public static String    DATAMAP_KEY_RECEIVER    = "receiver";  // 接受者
    public static final int EXEC_RESULT_SUCC        = 1;           //执行成功
    public static final int EXEC_RESULT_FAIL        = 2;           //执行失败
    //发送方式 '0:站内消息,1:邮件,2:短信'	
    public static final int SENDTYPE_MESSAGE        = 0;           //站内消息
    public static final int SENDTYPE_MAIL           = 1;           //邮件
    public static final int SENDTYPE_SMS            = 2;           //短信
    /** 今天 */
    String                  todayString             = "0";
    /** 昨天 */
    String                  yestodayString          = "1";
    /**  近一周 */
    String                  weekString              = "2";
    /** 近一月  */
    String                  monthString             = "3";
    /**  自由 */
    String                  freeString              = "4";
}
