package com.orangelabs.tuya2capi.tuya2cApi.baseresponse;

import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.orangelabs.reservation.base.responsebody.BaseResponse;
import com.orangelabs.reservation.base.responsebody.ResponseData;
import com.orangelabs.reservation.base.responsebody.ResultEnums;

// tong yi xiang ying ti chu li
@ControllerAdvice(annotations = BaseResponse.class)
public class ResponseResultHandlerAdvice implements ResponseBodyAdvice{
	
	private Logger log = Logger.getLogger(ResponseResultHandlerAdvice.class);

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		if(MediaType.APPLICATION_JSON.equals(selectedContentType) || MediaType.APPLICATION_JSON_UTF8.equals(selectedContentType)){ // 判断响应的Content-Type为JSON格式的body
            if(body instanceof ResponseData){ // 如果响应返回的对象为统一响应体，则直接返回body
            	log.info("return ResponseData directly");
                return body;
            }else{
            	ResponseData responseResult =new ResponseData(ResultEnums.SUCCESS.getCode(),ResultEnums.SUCCESS.getMsg(),body);
                return responseResult;
            }
        }
        // 非JSON格式body直接返回即可
        return body;
	}
	
	

}
