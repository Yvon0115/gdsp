/**
 * 
 */
package com.gdsp.integration.framework.reportentry.model;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.AuditableEntity;


/**
 * 历史变更VO
 * @author wangliyuan
 *
 */
public class HistoryChangeVO extends AuditableEntity{
    /**
     * 
     */
    private static final long serialVersionUID = -4085672285214952150L;
    private String  title;
    private String  comments;
    private String  opType;
    private String  link_id;
    private String  changeTime;
    private String  username;
    private String  report_name;
    private String  state;//状态
    private String  changeName;//变更申请人
    private DDateTime  operationTime;//操作时间
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public String getOpType() {
        return opType;
    }
    
    public void setOpType(String opType) {
        this.opType = opType;
    }
    
    public String getLink_id() {
        return link_id;
    }
    
    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }
    
    public String getChangeTime() {
        return changeTime;
    }
    
    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getReport_name() {
        return report_name;
    }
    
    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    
    public String getState() {
        return state;
    }

    
    public void setState(String state) {
        this.state = state;
    }

    
    public String getChangeName() {
        return changeName;
    }

    
    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    
    public DDateTime getOperationTime() {
        return operationTime;
    }

    
    public void setOperationTime(DDateTime operationTime) {
        this.operationTime = operationTime;
    }
    
}
