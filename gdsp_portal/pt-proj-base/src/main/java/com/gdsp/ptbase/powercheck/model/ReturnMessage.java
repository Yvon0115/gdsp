package com.gdsp.ptbase.powercheck.model;


public class ReturnMessage {
    public static final int   code_success        = 0;
    public static final int   code_user_error     = 360;
    public static final int   code_password_error = 180;
    public static final int   code_power_error    = 90;
    public static final int   code_ST_error       = 45;
    
    private int code;
    private String message;
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
}
