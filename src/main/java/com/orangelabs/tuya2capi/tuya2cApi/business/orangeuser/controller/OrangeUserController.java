package com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.controller;

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

import com.orangelabs.reservation.base.responsebody.BaseResponse;
import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.req.UserRequest;
import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.service.OrangeUserService;

@BaseResponse
@RestController
@RequestMapping("/users")
public class OrangeUserController {
	
	private Logger log = Logger.getLogger(OrangeUserController.class);
	
	@Autowired
	private OrangeUserService orangeUserService;

	@RequestMapping(value = "/admin", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> createAdmin(@RequestBody UserRequest request) throws Exception {
		log.info("create admin users ");
		Map<String, Object> map = orangeUserService.createAdminUser(request);
		return map;
	}
	
	@RequestMapping(value = "/authenticate", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> login(@RequestBody UserRequest request) throws Exception {
		log.info("user to login ");
		Map<String, Object> map = orangeUserService.login(request);
		return map;
	}
	
	@RequestMapping(value = "", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getAllUsers(HttpServletRequest request) throws Exception {
		log.info("get all users ");
		//String token = request.getHeader("Authorization");
		Map<String, Object> map = orangeUserService.getAllUsers();
		return map;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getUserDetail(@PathVariable String id) throws Exception {
		log.info("get user detail ");
		Map<String, Object> map = orangeUserService.getUserDetail(id);
		return map;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE})
	@ResponseBody
	public Map<String, Object> deleteUser(@PathVariable String id) throws Exception {
		log.info("delete user ");
		Map<String, Object> map = orangeUserService.deleteUser(id);
		return map;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT})
	@ResponseBody
	public Map<String, Object> updateUser(@PathVariable String id, @RequestBody UserRequest request) throws Exception {
		log.info("update user ");
		Map<String, Object> map = orangeUserService.updateUser(id, request);
		return map;
	}
	
	@RequestMapping(value = "/{id}/password", method = { RequestMethod.PUT})
	@ResponseBody
	public Map<String, Object> updatePassword(@PathVariable String id, @RequestBody UserRequest request) throws Exception {
		log.info("update user password");
		Map<String, Object> map = orangeUserService.updateUser(id, request);
		return map;
	}
	
	@RequestMapping(value = "/{id}/roleandcountry", method = { RequestMethod.PUT})
	@ResponseBody
	public Map<String, Object> updateRoleAndCountry(@PathVariable String id, @RequestBody UserRequest request) throws Exception {
		log.info("update user role and country");
		Map<String, Object> map = orangeUserService.updateUser(id, request);
		return map;
	}

}
