package com.orangelabs.tuya2capi.tuya2cApi.exception;

import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.ResultEnums;

public class BussinessException extends RuntimeException
{

    private static final long serialVersionUID = 477399363086602984L;
    
    private ResultEnums code;
    
    private String msg;

    public BussinessException()
    {
        super("System exception");
    }

    public BussinessException(String message)
    {
        super(message);
    }

    public BussinessException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public BussinessException(ResultEnums code) {
        this.code = code;
    }
    
    public BussinessException(ResultEnums code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BussinessException(Throwable cause, ResultEnums code) {
        super(cause);
        this.code = code;
    }

	public ResultEnums getCode() {
		return code;
	}

	public void setCode(ResultEnums code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
