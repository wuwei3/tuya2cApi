package com.orangelabs.tuya2capi.tuya2cApi.business.fav.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.BaseResponse;
import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.ResultEnums;
import com.orangelabs.tuya2capi.tuya2cApi.business.fav.req.FavRequest;
import com.orangelabs.tuya2capi.tuya2cApi.business.fav.service.UserFavService;
import com.orangelabs.tuya2capi.tuya2cApi.exception.BussinessException;

@BaseResponse
@RestController
public class UserFavController {
	
	private Logger log = Logger.getLogger(UserFavController.class);
	
	@Autowired
	private UserFavService userFavService;
	
	@RequestMapping(value = "/fav/{userId}", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getUserFavList(@PathVariable String userId) throws Exception {
		log.info("get user fav list, userId " + userId);
		Map<String, Object> map = userFavService.getUserFavList(userId);
		return map;
	}
	
	@RequestMapping(value = "/favbyProductId/{productId}", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getUserIsFavThisProduct(@PathVariable String productId) throws Exception {
		log.info("to check this user is whether fav this product, productId " + productId);
		Map<String, Object> map = userFavService.getUserIsFavThisProduct(productId);
		return map;
	}
	
	@RequestMapping(value = "/fav", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> addFavToList(@RequestBody FavRequest request, HttpServletRequest servletRequest) throws Exception {
		log.info("add fav dao list ");
		
		String token = servletRequest.getHeader("Authorization");
		if (token == null || "".equals(token)) {
			throw new BussinessException(ResultEnums.BUSSINESS_ERROR, "Please login first, then do the operation");
		}
		
		Map<String, Object> map = userFavService.addFavToList(request);
		return map;
	}
	
	@RequestMapping(value = "/fav/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	public Map<String, Object> delFavToList(@PathVariable String id) throws Exception {
		log.info("delete  fav dao list ");
		Map<String, Object> map = userFavService.deleteFavToList(id);
		return map;
	}

}
