package com.gdsp.platform.workflow.utils;

/**
 * 工作流常量
 * @author wqh
 * @version 1.0 2017年12月6日 下午5:48:33
 * @since 1.7
 */
public interface WorkflowConst {

	/** xml标签key */
	public interface XMLDefinition{
		/** 通知方式名称 - 系统通知 */
        public final static String KEY_SYSTEM_INFORM      = "systeminform";
        /** 通知方式名称 - 邮件通知 */
        public final static String KEY_EMAIL_INFORM       = "emailinform";
        
	}
	
	/** 节点通知 */
    public interface InformDealPerson {

    	/** 系统通知 */
        public final static int SYSTEM_INFORM       = 1;
        /** 邮件通知 */
        public final static int EMAIL_INFORM        = 2;
        /** 两种通知 */
        public final static int SYSTEM_EMAIL_INFORM = 3;
    }

    /** 超时处理 */
    public interface DealMethod {

    	/** 通知待办人 */
        public final static int INFORM_PERSON        = 1;
        /** 自动通过 */
        public final static int AUTO_PASS            = 2;
        /** 自动不通过 */
        public final static int AUTO_FAIL            = 3;
        /** 自动驳回上级 */
        public final static int AUTO_RETURN_SUPERIOR = 4;
        /** 自动驳回发起人 */
        public final static int AUTO_RETURN_PERSON   = 5;
    }

    /** 流程状态 */
    public interface WorkFlowStatus {

        /** 停用 */
        public final static int DISABLED   = 0;
        /** 启用 */
        public final static int ENABLED = 1;
    }

    /** 审批结果  */
    public interface FlowResult {
        /** 审批通过*/
        public final static int AGREE             = 1;
        /** 审批不通过*/
        public final static int REJECT            = 2;
        /** 驳回发起人*/
        public final static int SENDBACK_SPONSOR  = 3;
        /** 驳回上一节点*/
        public final static int SENDBACK_PREVIOUS = 4;
        /** 发起流程*/
        public final static int STARTPROCESS      = 5;
        /** 发起人撤回*/
        public final static int RETRACT           = 6;
        /** 管理员通过*/
        public final static int ADMINAGREE        = 7;
        /** 管理员不通过*/
        public final static int ADMINREJECT       = 8;
        /** 管理员驳回发起人*/
        public final static int ADMINSENDBACK_SPONSOR = 9;
        /** 发起人重新发起流程*/
        public final static int RESTARTPROCESSINST    = 10;
        /** 系统自动通过*/
        public final static int SYSTEMAGREE        = 11;
        /** 系统自动不通过*/
        public final static int SYSTEMREJECT       = 12;
        /** 系统驳回发起人*/
        public final static int SYSTEMSENDBACK_SPONSOR = 13;
        /** 系统驳回上一节点*/
        public final static int SYSTEMSENDBACK_PREVIOUS = 14;

    }
}
