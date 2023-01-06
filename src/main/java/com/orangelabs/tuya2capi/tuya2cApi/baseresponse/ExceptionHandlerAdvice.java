package com.orangelabs.tuya2capi.tuya2cApi.baseresponse;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.orangelabs.reservation.base.responsebody.BaseResponse;
import com.orangelabs.reservation.base.responsebody.ResponseData;
import com.orangelabs.reservation.base.responsebody.ResultEnums;
import com.orangelabs.reservation.exception.BussinessException;


// tong yi yi chang chu li
@ControllerAdvice(annotations = BaseResponse.class)
@ResponseBody
public class ExceptionHandlerAdvice {
	
	private Logger log = Logger.getLogger(ExceptionHandlerAdvice.class);
	
	@ExceptionHandler(Exception.class)
    public ResponseData handleException(Exception e){
        log.error(e.getMessage(),e);
        return new ResponseData(ResultEnums.SYSTEM_ERROR.getCode(),e.getMessage(),null);
    }
	
	@ExceptionHandler(RuntimeException.class)
    public ResponseData handleRuntimeException(RuntimeException e){
        log.error(e.getMessage(),e);
        return new ResponseData(ResultEnums.SYSTEM_ERROR.getCode(),e.getMessage(),null);
    }
	
	@ExceptionHandler(BussinessException.class)
    public ResponseData handleBaseException(BussinessException e){
        log.error("error message " + e.getMessage());
        ResultEnums code=e.getCode();
        String msg = e.getMsg();
        //return new ResponseData(code.getCode(),code.getMsg(),null);
        return new ResponseData(code.getCode(),msg,null);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData handleBindException(MethodArgumentNotValidException e){
        log.error("error message " + e.getMessage());
        
        List<ObjectError> list = e.getBindingResult().getAllErrors();
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (ObjectError oe: list) {
        	String errormsg = oe.getDefaultMessage();
        	sb.append(errormsg + ",");
        }
        sb.append("]");
        log.info("param failed " + sb.toString());
        return new ResponseData(ResultEnums.PARAM_ERROR.getCode(),sb.toString(),null);
    }
	
	@ExceptionHandler(BindException.class)
    public ResponseData handleBind2Exception(BindException e){
        log.error("error message2 " + e.getMessage());
        
        List<ObjectError> list = e.getBindingResult().getAllErrors();
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (ObjectError oe: list) {
        	String errormsg = oe.getDefaultMessage();
        	sb.append(errormsg + ",");
        }
        sb.append("]");
        log.info("param failed2 " + sb.toString());
        
        return new ResponseData(ResultEnums.PARAM_ERROR.getCode(),sb.toString(),null);
    }

}
