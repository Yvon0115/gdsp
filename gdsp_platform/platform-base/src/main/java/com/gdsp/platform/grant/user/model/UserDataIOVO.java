package com.gdsp.platform.grant.user.model;

import com.gdsp.dev.core.utils.excel.AbstractDataIOVO;
import com.gdsp.dev.core.utils.excel.ExcelVOAttribute;

public class UserDataIOVO implements AbstractDataIOVO {

    @ExcelVOAttribute(name = "账号", column = "A", isExport = true, isNo = true)
    private String account;
    @ExcelVOAttribute(name = "用户名", column = "B", isExport = true)
    private String username;
    @ExcelVOAttribute(name = "密码", column = "C", isExport = true)
    private String user_password;
    @ExcelVOAttribute(name = "手机", column = "D", isExport = true)
    private String mobile;
    @ExcelVOAttribute(name = "固定电话", column = "E", isExport = true)
    private String tel;
    @ExcelVOAttribute(name = "邮箱", column = "F", isExport = true)
    private String email;
    @ExcelVOAttribute(name = "性别", column = "G", isExport = true)
    private String sex;
    @ExcelVOAttribute(name = "所属机构", column = "H", isExport = true)
    private String pk_org;
    @ExcelVOAttribute(name = "机构编码", column = "I", isExport = true)
    private String orgCode;
    @ExcelVOAttribute(name = "描述", column = "J", isExport = true)
    private String memo;
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPk_org() {
        return pk_org;
    }

    public void setPk_org(String pk_org) {
        this.pk_org = pk_org;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
