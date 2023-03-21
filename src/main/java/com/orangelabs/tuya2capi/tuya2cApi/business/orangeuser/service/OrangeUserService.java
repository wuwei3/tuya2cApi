package com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.ResultEnums;
import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.mapping.OrangeUserMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.model.OrangeUser;
import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.req.UserRequest;
import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.resp.OrangUserType;
import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.resp.UserResp;
import com.orangelabs.tuya2capi.tuya2cApi.exception.BussinessException;
import com.orangelabs.tuya2capi.tuya2cApi.utils.MD5Util;
import com.orangelabs.tuya2capi.tuya2cApi.utils.TokenProccessor;

@Service
public class OrangeUserService {
	
	private Logger log = Logger.getLogger(OrangeUserService.class);
	
	@Autowired
	private OrangeUserMapper orangeUserMapper;
	
	public Map<String, Object> createAdminUser(UserRequest req) throws Exception {
		log.info("create or update by admin user " + JSONObject.toJSONString(req));
		String id = req.getAccountId();
		if (id !=null && !"".equals(id)) {
			log.info("updat user id " + id);
			return updateUser(id, req);
		} else {
			log.info("create new  user " );
			OrangeUser ou = createUserEntity(req, true);
			orangeUserMapper.insert(ou);
			
			log.info("create user id " + ou.getOrangeUserId());
			
			UserResp resp = getUserResp(ou,true);
			
			Map<String, Object> map = getRespMap(resp, ou.getToken(),"User admin created");
			return map;
		}
	}
	
	public Map<String, Object> login(UserRequest req) throws Exception {
		log.info("user login " + JSONObject.toJSONString(req));
		String email = req.getEmail();
		String password = req.getPassword();
		
		Map<String, Object> query = new HashMap<>();
		query.put("email", email);
		
		String encryptpwd = MD5Util.getMD5Code(password);
		query.put("password", encryptpwd);
		
		List<OrangeUser> users = orangeUserMapper.selectByCondition(query);
		if (users == null || users.size() == 0) {
			throw new BussinessException(ResultEnums.BUSSINESS_ERROR, "email or password not correct");
		} else {
			OrangeUser ou = users.get(0);
			UserResp resp = getUserResp(ou,true);
			Map<String, Object> map = getRespMap(resp, ou.getToken(),"User authenticated");
			
			return map;
		}
	}
	
	public Map<String, Object> getAllUsers() throws Exception {
		log.info("get all users ");
		
		List<UserResp> usersResp = new ArrayList<>();
		
		List<OrangeUser> users = orangeUserMapper.selectByCondition(new HashMap<>());
		if (users != null && users.size() > 0) {
			for (OrangeUser user: users) {
				UserResp resp = getUserResp(user,false);
				usersResp.add(resp);
			}
		} else {
			log.info("user list no data!!!!");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("users", usersResp);
		map.put("message", "Users list");
		
		return map;
	}
	
	public Map<String, Object> getUserDetail(String id) throws Exception {
		log.info("get user detail  " + id);
		OrangeUser user = getUserById(id);
		UserResp resp = getUserResp(user,false);
		Map<String, Object> map = getRespMap(resp, user.getToken(),"Get user detail success");
		return map;
	}
	
	public OrangeUser getUserById(String id) throws Exception {
		log.info("get user by id " + id);
		OrangeUser user = orangeUserMapper.selectByPrimaryKey(Long.valueOf(id));
		if (user == null) {
			throw new BussinessException(ResultEnums.BUSSINESS_ERROR, "user not exist");
		} 
		
		return user;
	}
	
	public Map<String, Object> deleteUser(String id) throws Exception {
		log.info("delete the user,id " + id);
		orangeUserMapper.deleteByPrimaryKey(Long.valueOf(id));
		Map<String, Object> map = new HashMap<>();
		map.put("message", "User delete");
		return map;
	}
	
	public Map<String, Object> updateUser(String id, UserRequest req) throws Exception {
		log.info("update the user, id " + id);
		log.info("update the user, req " + JSONObject.toJSONString(req));
		OrangeUser user = getUserById(id);
		
		String firstname = req.getFirstname();
		if (firstname != null && !"".equals(firstname)) {
			user.setFirstName(firstname);
		}
		
		String lastname = req.getLastname();
		if (lastname != null && !"".equals(lastname)) {
			user.setLastName(lastname);
		}
		
		String password = req.getPassword();
		if (password != null && !"".equals(password)) {
			String encrypt = MD5Util.getMD5Code(password);
			user.setPassword(encrypt);
		}
		
		String email = req.getEmail();
		if (email != null && !"".equals(email)) {
			user.setEmail(email);
		}
				
		String country = req.getCountry();
		if (country != null && !"".equals(country)) {
			user.setCountry(country);
		}
		
		String role = req.getRole();
		if (role != null && !"".equals(role)) {
			user.setUserRole(role);
		}
		
		user.setUpdateTime(new Date());
		
		orangeUserMapper.updateByPrimaryKey(user);
		
		Map<String, Object> map = new HashMap<>();
		map.put("message", "User update");
		return map;
	}
	
	
	
	private Map<String, Object> getRespMap(UserResp resp, String token, String msg) {
		Map<String, Object> map = new HashMap<>();
		map.put("user", resp);
		map.put("token", token);
		map.put("message", msg);
		
		return map;
	}
	
	private OrangeUser createUserEntity(UserRequest req, boolean isNew) {
		
		OrangeUser ou = new OrangeUser();
		ou.setFirstName(req.getFirstname());
		ou.setLastName(req.getLastname());
		ou.setEmail(req.getEmail());
		
		String password = req.getPassword();
		if (password == null || "".equals(password)) {
			password = req.getEmail(); // yong email zuo wei mi ma
		}
		
		String encryptpwd = MD5Util.getMD5Code(password);
		ou.setPassword(encryptpwd);
		
		ou.setUserRole(req.getRole());
		
		String country = req.getCountry();
		if (country != null && !"".equals(country)) {
			ou.setCountry(country);
		}
		
		if (isNew) {
			ou.setCreateTime(new Date());
		}
		
		ou.setUpdateTime(new Date());
		
		String token = TokenProccessor.getInstance().makeToken();
		ou.setToken(token);
		
		return ou;
	}
	
	
	private UserResp getUserResp(OrangeUser ou, boolean needPwd) {
		OrangUserType type = new OrangUserType();
		
		type.setCountry(ou.getCountry());
		type.setRole(ou.getUserRole());
		
		UserResp resp = new UserResp();
		resp.set_id(ou.getOrangeUserId().toString());
		resp.setFirstname(ou.getFirstName());
		resp.setLastname(ou.getLastName());
		resp.setEmail(ou.getEmail());
		if (needPwd) {
			resp.setPassword(ou.getPassword());
		}
		resp.setType(type);
		resp.setRole(ou.getUserRole());
		resp.setCountry(ou.getCountry());
		
		return resp;
	}
	

}
