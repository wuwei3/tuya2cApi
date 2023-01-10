package com.orangelabs.tuya2capi.tuya2cApi.baseresponse;

public enum ResultEnums {
	
    SUCCESS("2000", "Request success"),
    ERROR("2001", "Request failed"), 
	SYSTEM_ERROR("5005", "System Exception"), 
	BUSSINESS_ERROR("2004", "Business Error"), 
    PARAM_ERROR("2003", "Parameter Error");
	
    private String code;
    
    private String msg;
 
    ResultEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getMsg() {
        return msg;
    }
 
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
