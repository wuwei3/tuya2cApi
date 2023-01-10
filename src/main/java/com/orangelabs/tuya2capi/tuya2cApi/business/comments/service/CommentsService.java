package com.orangelabs.tuya2capi.tuya2cApi.business.comments.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.ResultEnums;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.mapping.OrangeCommentMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.model.OrangeComment;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.req.CommentReqt;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.resp.CommentEditResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.resp.CommentListResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.mapping.OrangeUserMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.model.OrangeUser;
import com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.resp.OrangUserType;
import com.orangelabs.tuya2capi.tuya2cApi.exception.BussinessException;

@Service
public class CommentsService {
	
    private Logger log = Logger.getLogger(CommentsService.class);
	
	@Autowired
	private OrangeCommentMapper orangeCommentMapper;
	
	@Autowired
	private OrangeUserMapper orangeUserMapper;
	
	public Map<String, Object> getCommentsList(String productId) throws Exception {
		log.info("get comment list, productId " + productId);
		
		Map<String, Object> map = new HashMap<>();
		map.put("productId", Long.valueOf(productId));
		
		List<OrangeComment> list = orangeCommentMapper.selectCommentByCondition(map);
		
		List<CommentListResp> result = getCommentResp(list);
		
		Map<String, Object> mapResult = new HashMap<>();
		mapResult.put("comments", result);
		
		return mapResult;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> createOrUpdateComment(CommentReqt reqt, boolean update, boolean del) throws Exception{
		log.info("edit comment " + JSONObject.toJSONString(reqt));
		Map<String, Object> map = new HashMap<>();
		if (!del) {
			log.info("create or update comment");
			CommentEditResp resp = editComment(reqt, update);
			if (!update) {
				map.put("comment", resp);
				map.put("message", "Comment created");
			} else {
				map.put("message", "Comment updated");
			}
		} else {
			log.info("del comment");
			String commentid = reqt.getCommentId();
			orangeCommentMapper.deleteByPrimaryKey(Long.valueOf(commentid));
			map.put("message", "delete succcess");
		}
		
		return map;
	}
	
	private CommentEditResp editComment(CommentReqt reqt, boolean update) throws Exception{
		OrangeComment oc = createCommentEntity(reqt, update);
		
		if (!update) {
			log.info("create comment");
			orangeCommentMapper.insert(oc);
		} else {
			log.info("update comment");
			orangeCommentMapper.updateByPrimaryKeyWithBLOBs(oc);
		}
		
		return transferToFrontResp(oc);
	}
	
	private CommentEditResp transferToFrontResp(OrangeComment oc) {
		
		CommentEditResp resp = new CommentEditResp();
		resp.set_id(oc.getCommentId().toString());
		resp.setContent(oc.getCommentContent());
		resp.setCountry(oc.getCountry());
		resp.setProductId(oc.getProductId().toString());
		resp.setRating(oc.getCommentRating());
		resp.setRole(oc.getUserRole());
		
		return resp;
	}
	
	private OrangeComment createCommentEntity(CommentReqt reqt, boolean update) throws Exception{
		
		OrangeComment oc = new OrangeComment();
		
		if (update) {
			String commenid = reqt.getCommentId();
			oc = orangeCommentMapper.selectByPrimaryKey(Long.valueOf(commenid));
		} else {
			
			String productid = reqt.getProductId();
			if (productid != null && !"".equals(productid)) {
				oc.setProductId(Long.valueOf(productid));
			}
			
			String userid = reqt.getUserId();
			if (userid != null && !"".equals(userid)) {
				oc.setOrangeUserId(Long.valueOf(userid));
			}
			
			OrangeUser user = getUserById(Long.valueOf(userid));
			oc.setUserRole(user.getUserRole());
			oc.setCountry(user.getCountry());
			oc.setCreateTime(new Date());
		}
		
		String content = reqt.getContent();
		if (content != null && !"".equals(content)) {
			oc.setCommentContent(content);
		}
		
		String rating = reqt.getRating();
		if (rating != null && !"".equals(rating)) {
			oc.setCommentRating(Integer.parseInt(rating));
		}
		
		String label = reqt.getLabel();
		if (label != null && !"".equals(label)) {
			oc.setCommentLabel(label);
		}
		
		oc.setUpdateTime(new Date());
		
		return oc;
	}
	
	private List<CommentListResp> getCommentResp(List<OrangeComment> list) throws Exception {
		List<CommentListResp> result = new ArrayList<>();
		
		if (list!= null && list.size() > 0) {
			for (OrangeComment oc: list) {
				CommentListResp resp = new CommentListResp();
				
				Long userid = oc.getOrangeUserId();
				Map<String, Object> userMap = getUserType(userid);
				
				resp.setUser_id(userMap);
				resp.setCountry(oc.getCountry());
				resp.set_id(oc.getCommentId().toString());
				resp.setContent(oc.getCommentContent());
				resp.setRating(oc.getCommentRating());
				resp.setProductId(oc.getProductId().toString());
				resp.setRole(oc.getUserRole());
				result.add(resp);
			}
		}
		
		return result;
	}
	
	
	private Map<String, Object> getUserType(Long userId) throws Exception {
		log.info("get user type by id " + userId);
		
		OrangeUser user = getUserById(userId);
		
		OrangUserType type = new OrangUserType();
		type.setCountry(user.getCountry());
		type.setRole(user.getUserRole());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("_id", user.getOrangeUserId());
		map.put("firstname", user.getFirstName());
		map.put("lastname", user.getLastName());
		
		return map;
	}
	
	private OrangeUser getUserById(Long userId) throws Exception {
		OrangeUser user = orangeUserMapper.selectByPrimaryKey(userId);
		if (user == null) {
			throw new BussinessException(ResultEnums.BUSSINESS_ERROR, userId + " not exist");
		}
		
		return user;
	}

}
