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
import com.orangelabs.tuya2capi.tuya2cApi.business.products.mapping.OrangeProductMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.mapping.OrangeProductParamMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.model.OrangeProduct;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.model.OrangeProductParam;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.req.ParamReq;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.req.ProductReq;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ParamsResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ProductResp;
import com.orangelabs.tuya2capi.tuya2cApi.exception.BussinessException;

@Service
public class ProductService {
	
	private Logger log = Logger.getLogger(ProductService.class);
	
	@Autowired
	private OrangeProductMapper orangeProductMapper;
	
	@Autowired
	private OrangeProductParamMapper orangeProductParamMapper;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public void createProduct(ProductReq req) throws Exception {
		log.info("create new product " + req.getName());
		
		OrangeProduct op = getProductEntity(req);
		
		orangeProductMapper.insert(op);
		log.info("product id " + op.getProductId());
		
		OrangeProductParam opp = getParamsEntity(op.getProductId(), req.getParams());
		orangeProductParamMapper.insert(opp);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> deleteProduct(String productId) throws Exception {
		log.info("delete product, id " + productId);
		
		orangeProductParamMapper.deleteByProductId(Long.valueOf(productId));
		orangeProductMapper.deleteByPrimaryKey(Long.valueOf(productId));
		
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
		
		op.setCreateTime(new Date());
		op.setUpdateTime(new Date());
		
		return op;
	}
	
	private OrangeProductParam getParamsEntity(Long productId, List<ParamReq> params) {
		
		OrangeProductParam opp = new OrangeProductParam();
		
		String paramswithid = genIDInPrarams(params);
		if (paramswithid != null && !"".equals(paramswithid)) {
			opp.setProductParamContent(paramswithid);
		}
		opp.setProductId(productId);
		opp.setCreateTime(new Date());
		opp.setUpdateTime(new Date());
		
		return opp;
	}
	
	private String genIDInPrarams(List<ParamReq> params) {
		
		String result = "";
		
		if (params != null && params.size() > 0) {
			String jsonparams = JSON.toJSONString(params);
			JSONArray ja = JSON.parseArray(jsonparams);
			
			for (int i =0;i<ja.size();i++) {
				JSONObject jo = ja.getJSONObject(i);
				int id = i + 1;
				jo.put("_id", String.valueOf(id));
			}
			
			result = JSON.toJSONString(ja);
		}
		
		System.out.println("result " + result);
		
		return result;
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
		
		List<ParamsResp> list = new ArrayList<>();
		
		List<OrangeProductParam> paramList = orangeProductParamMapper.getParamsByProductId(productId);
		
		if (paramList !=null && paramList.size() > 0) {
			OrangeProductParam pa= paramList.get(0);
			String ps = pa.getProductParamContent();
			list = JSON.parseArray(ps, ParamsResp.class);
		}
		return list;
	}
	

}
