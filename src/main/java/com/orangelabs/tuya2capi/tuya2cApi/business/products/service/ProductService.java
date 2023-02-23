package com.orangelabs.tuya2capi.tuya2cApi.business.products.service;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.ResultEnums;
import com.orangelabs.tuya2capi.tuya2cApi.business.comments.mapping.OrangeCommentMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.mapping.OrangeProductMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.mapping.OrangeProductParam2vMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.model.OrangeProduct;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.model.OrangeProductParam2v;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.req.ParamReq;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.req.ProductReq;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.DistinctParamKeyResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ParamsExistsResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ParamsResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ProductResp;
import com.orangelabs.tuya2capi.tuya2cApi.exception.BussinessException;

@Service
public class ProductService {
	
	private Logger log = Logger.getLogger(ProductService.class);
	
	@Autowired
	private OrangeProductMapper orangeProductMapper;
	
	@Autowired
	private OrangeProductParam2vMapper orangeProductParam2vMapper;
	
	@Autowired
	private OrangeCommentMapper orangeCommentMapper;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public void createProduct(ProductReq req) throws Exception {
		log.info("create new product " + req.getName());
		
		OrangeProduct op = getProductEntity(req);
		
		orangeProductMapper.insert(op);
		log.info("product id " + op.getProductId());
		
		insertParamsEntity(op.getProductId(), req.getParams());
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> deleteProduct(String productId) throws Exception {
		log.info("delete product, id " + productId);
		
		orangeProductParam2vMapper.deleteByProductId(Long.valueOf(productId));
		orangeProductMapper.deleteByPrimaryKey(Long.valueOf(productId));
		
		orangeCommentMapper.deleteByProductId(Long.valueOf(productId));
		
		Map<String, Object> map = new HashMap<>();
		map.put("message", "delete success");
		
		return map;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> updateProduct(String productId, ProductReq req) throws Exception {
		log.info("update product, id " + productId + " name " + req.getName() + "  price  " + req.getPrice());
		
		OrangeProduct op = getProductById(Long.valueOf(productId));
		
		String name = req.getName();
		if (name != null && !"".equals(name)) {
			op.setProductName(name);
		}
		
		String price = req.getPrice();
		if (price != null && !"".equals(price)) {
			op.setProductPrice(price);
		}
		
		op.setUpdateTime(new Date());
		
		orangeProductMapper.updateByPrimaryKey(op);
		
		Map<String, Object> map = new HashMap<>();
		map.put("message", "update success");
		
		return map;
	}
	
	private OrangeProduct getProductEntity(ProductReq req) {
		
		OrangeProduct op = new OrangeProduct();
		
		String name = req.getName();
		if (name != null && !"".equals(name)) {
			op.setProductName(name);
		}
		
		String path = req.getPath();
		if (path != null && !"".equals(path)) {
			op.setProductPath(path);
		}
		
		String imgurl = req.getImgUrl();
		if (imgurl != null && !"".equals(imgurl)) {
			op.setImgUrl(imgurl);
		}
		
		String price = req.getPrice();
		if (price != null && !"".equals(price)) {
			op.setProductPrice(price);
		}
		
		List<String> tags = req.getTags();
		if (tags != null && tags.size() > 0) {
			String ptags = String.join(",", tags);
			op.setProductTags(ptags);
		}
		
		String base64 = req.getImageBase64();
		if (base64 != null && !"".equals(base64)) {
			byte[] bc = base64.getBytes();
			op.setImgBlob(bc);
		}
		
		op.setCreateTime(new Date());
		op.setUpdateTime(new Date());
		
		return op;
	}
	
	private void insertParamsEntity(Long productId, List<ParamReq> params) throws Exception{
		if (params != null && params.size() > 0) {
			for (int i =0;i<params.size();i++) {
				ParamReq jo = params.get(i);
				String paramName = jo.getName();
				
				List<String> vals = jo.getVals(); // vals
				for (int j =0;j<vals.size();j++) {
					String val = (String)vals.get(j);
					insertSingelParamEntity(productId, paramName, val);
				}
			}
		}
	}
	
	
	public Map<String, Object> getAllProducts(String queryPath) throws Exception {
		log.info("get product method entry " + queryPath);
		
		List<ProductResp> products = getProductRespEntity(queryPath);
		
		Map<String, Object> map = new HashMap<>();
		map.put("products", products);
		
		return map;
	}
	
	public Map<String, Object> getProductDetail(String id) throws Exception {
		log.info("get product detail " + id);
		
		OrangeProduct op = getProductById(Long.valueOf(id));
		
		ProductResp resp = getSingelProductResp(op);
		
		Map<String, Object> map = new HashMap<>();
		map.put("product", resp);
		map.put("message", "Details product");
		
		return map;
	}
	
	
	public OrangeProduct getProductById(Long productId) throws Exception {
		
		OrangeProduct op = orangeProductMapper.selectByPrimaryKey(productId);
		if (op == null) {
			throw new BussinessException(ResultEnums.BUSSINESS_ERROR, productId + " not exist");
		}
		
		return op;
	}
	
	
	private List<ProductResp> getProductRespEntity(String queryPath) {
		log.info("get product resp entity " + queryPath);
		Map<String, Object> map = new HashMap<>();
		if (queryPath != null && !"all".equals(queryPath)) {
			map.put("path", queryPath);
		}
		List<OrangeProduct> listfromdb = orangeProductMapper.selectProductByCondition(map);
		if (listfromdb == null || listfromdb.size() ==0) {
			log.info(queryPath + " no data under it!!");
		}
		
		return transferdata(listfromdb);
	}
	
	
	private List<ProductResp> transferdata(List<OrangeProduct> listfromdb) {
		List<ProductResp> result = new ArrayList<>();
		
		if (listfromdb != null && listfromdb.size() > 0) {
			for (OrangeProduct op : listfromdb) {
				ProductResp resp = getSingelProductResp(op);
			    result.add(resp);
			}
		}
		return result;
	}
	
	
	public ProductResp getSingelProductResp(OrangeProduct op) {
		
		ProductResp resp = new ProductResp();
		
	    resp.set_id(op.getProductId().toString());
	    resp.setName(op.getProductName());
	    resp.setImgUrl(op.getImgUrl());
	    resp.setPrice(op.getProductPrice());
	    resp.setPath(op.getProductPath());
	    byte[] image = op.getImgBlob();
		if (image != null && image.length > 0) {
			String imageToFront = new String(image);
			resp.setImageBase64(imageToFront);
		}
	    
	    String tagsDB = op.getProductTags();
	    if (tagsDB != null && !"".equals(tagsDB)) {
	    	List<String> tags = JSON.parseArray(tagsDB, String.class);
	    	resp.setTags(tags);
	    } else {
	    	resp.setTags(new ArrayList<String>());
	    }
	    
	    List<ParamsResp> params = getParamsRespEntity(op.getProductId());
	    resp.setParams(params);
		
		return resp;
	}
	
	
	private List<ParamsResp> getParamsRespEntity(Long productId) {
		log.info("get params resp entity by product id " + productId);
		
		List<ParamsResp> listResult = new ArrayList<>();
		
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		
		List<DistinctParamKeyResp> paramList = orangeProductParam2vMapper.getDistinctKeysByConditions(map);
		
		if (paramList !=null && paramList.size() > 0) {
			for (int i =0;i<paramList.size();i++) {
				DistinctParamKeyResp pa = paramList.get(i);
				
				String paramKey = pa.getOrangeProductParamKey();
				map.put("productParamKey", paramKey);
				
				ParamsResp pr = new ParamsResp();
				List<String> vals = getVals(map);
				pr.set_id(String.valueOf(i+1)); // no use for late logic
				pr.setName(paramKey);
				if (vals != null && vals.size() > 0) {
					pr.setVals(vals);
				}
				listResult.add(pr);
			}
		}
		return listResult;
	}
	
	private List<String> getVals(Map<String, Object> map) {
		List<String> result = new ArrayList<>();
		
		List<OrangeProductParam2v> vals = orangeProductParam2vMapper.getValsByConditions(map);
		if (vals!= null && vals.size() > 0) {
			
			for (OrangeProductParam2v o2v: vals) {
				String val = o2v.getOrangeProductParamVals();
				
				if (val != null && !"".equals(val) && !"null".equals(val)) {
					result.add(val);
				}
			}
		}
		
		return result;
	}
	
	public void insertSingelParamEntity(Long productId, String key, String val) throws Exception{
		OrangeProductParam2v o2v = new OrangeProductParam2v();
		o2v.setOrangeProductId(productId);
		o2v.setOrangeProductParamKey(key);
		o2v.setOrangeProductParamVals(val);
		o2v.setCreateTime(new Date());
		o2v.setUpdateTime(new Date());
		
		orangeProductParam2vMapper.insert(o2v);
	}
	
	public void updateParams2v(OrangeProductParam2v o2v, String val) throws Exception{
		o2v.setOrangeProductParamVals(val);
		o2v.setUpdateTime(new Date());
		orangeProductParam2vMapper.updateByPrimaryKey(o2v);
	}
	
	public void deleteParams2v(OrangeProductParam2v o2v) throws Exception{
		log.info("delete the o2v , param id " + o2v.getOrangeProductParamId());
		orangeProductParam2vMapper.deleteByPrimaryKey(o2v.getOrangeProductParamId());
	}
	
	public ParamsExistsResp dataExistsWithKeyVal(Long productId, String key, String val) throws Exception{
		
		log.info("to find the params exists,  productId " + productId + ", key " + key + ", val "+ val);
		
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		map.put("productParamKey", key);
		
		if (val != null && !"".equals(val)) {
			map.put("productParamVal", val);
		}
		List<OrangeProductParam2v> list = orangeProductParam2vMapper.getParamsv2ByConditions(map);
		
		ParamsExistsResp resp = new ParamsExistsResp();
		
		if (list != null && list.size() > 0) {
			log.info("key and val exits");
			resp.setEixts(true);
			resp.setList(list);
		} else {
			log.info("key and val not exits");
			resp.setEixts(false);
		}
		
		return resp;
	}
}
