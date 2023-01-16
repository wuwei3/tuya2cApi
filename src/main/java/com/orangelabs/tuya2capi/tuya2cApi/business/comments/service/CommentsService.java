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
import com.orangelabs.tuya2capi.tuya2cApi.business.products.model.OrangeProductParam2v;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ParamsExistsResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.service.ProductService;
import com.orangelabs.tuya2capi.tuya2cApi.exception.BussinessException;
import com.orangelabs.tuya2capi.tuya2cApi.utils.ConstantUtil;

@Service
public class CommentsService {
	
    private Logger log = Logger.getLogger(CommentsService.class);
	
	@Autowired
	private OrangeCommentMapper orangeCommentMapper;
	
	@Autowired
	private OrangeUserMapper orangeUserMapper;
	
	@Autowired
	private ProductService productService;
	
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
			deleteParamv2AfterDeleteComment(Long.valueOf(reqt.getProductId()), Long.valueOf(reqt.getUserId()));
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
		
		Long productId = 0l;
		String pid = reqt.getProductId();
		if (pid != null && !"".equals(pid)) {
			productId = Long.valueOf(pid);
		}
		Integer rate = 0;
		String r = reqt.getRating();
		if (r != null && !"".equals(r)) {
			rate = Integer.parseInt(reqt.getRating());
		}
		createOrUpdateParamv2(reqt.getRole(),productId, rate, reqt.getLabel());
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
				resp.setLabel(oc.getCommentLabel());
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
		map.put("_id", user.getOrangeUserId().toString());
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
	
	private void createOrUpdateParamv2(String role, Long productId, Integer rating, String label) throws Exception {
		log.info("to impact the paramsv1 recommendation, role " + role + ",  productId " + productId + ", rating "+ rating);
		
		if (ConstantUtil.ROLE_PortfolioManager.equals(role)) {
			if (rating >= 3) {
				insertOrUpdateP2v(productId, ConstantUtil.Orange_Recommendation, ConstantUtil.Portfolio_Recommend,false);
			} else {
				deleteDataWithKeyVal(productId, ConstantUtil.Orange_Recommendation, ConstantUtil.Portfolio_Recommend, false);
			}
		} else if (ConstantUtil.ROLE_SupplyChainManager.equals(role)) {
			if (rating >= 3) {
				insertOrUpdateP2v(productId, ConstantUtil.Orange_Recommendation, ConstantUtil.SupplyChain_Recommend,false);
			} else {
				deleteDataWithKeyVal(productId, ConstantUtil.Orange_Recommendation, ConstantUtil.SupplyChain_Recommend,false);
			}
		} else if (ConstantUtil.ROLE_DeliveryManager.equals(role)) {
			if (label != null && !"".equals(label)) {
				insertOrUpdateP2v(productId, ConstantUtil.Orange_Validation, label,true);
			}
		}
	}
	
	private void deleteParamv2AfterDeleteComment(Long productId, Long userId) throws Exception {
		log.info("to delete the paramsv1 after the delete comment, userId " + userId + ",  productId " + productId);
		
		OrangeUser user = orangeUserMapper.selectByPrimaryKey(userId);
		String role = user.getUserRole();
		
		log.info("role is  " + role);
		
		if (ConstantUtil.ROLE_PortfolioManager.equals(role)) {
			deleteDataWithKeyVal(productId, ConstantUtil.Orange_Recommendation, ConstantUtil.Portfolio_Recommend, false);
		} else if (ConstantUtil.ROLE_SupplyChainManager.equals(role)) {
			deleteDataWithKeyVal(productId, ConstantUtil.Orange_Recommendation, ConstantUtil.SupplyChain_Recommend,false);
		} else if (ConstantUtil.ROLE_DeliveryManager.equals(role)) {
			deleteDataWithKeyVal(productId, ConstantUtil.Orange_Validation, "", true);
		}
	}
	
	private void insertOrUpdateP2v(Long productId, String key, String val, boolean isDeliverManager) throws Exception {
		ParamsExistsResp resp = null;
		
		if (isDeliverManager) {
			resp = productService.dataExistsWithKeyVal(productId, key, ""); // only need productid and key, the Orange Validation value is allow Orange Validated or Orange accessed
		} else {
			resp = productService.dataExistsWithKeyVal(productId, key, val); 
		}
		
		boolean exists = resp.isEixts();
		if (!exists) {
			productService.insertSingelParamEntity(productId, key, val);
		} else {
			List<OrangeProductParam2v> list = resp.getList();
			OrangeProductParam2v o2v = list.get(0);
			productService.updateParams2v(o2v, val);
		}
	}
	
	private void deleteDataWithKeyVal(Long productId, String key, String val, boolean isDeliverManager) throws Exception {
		log.info("delete the params,  productId " + productId + ", key " + key + ", val "+ val);
		ParamsExistsResp resp = productService.dataExistsWithKeyVal(productId, key, val);
		boolean exists = resp.isEixts();
		if (exists) {
			List<OrangeProductParam2v> list = resp.getList();
			OrangeProductParam2v o2v = list.get(0);
			if (isDeliverManager) {
				productService.updateParams2v(o2v, null);
			} else {
				productService.deleteParams2v(o2v);
			}
		} else {
			log.info("no need to delete anything");
		}
		
	}

}
