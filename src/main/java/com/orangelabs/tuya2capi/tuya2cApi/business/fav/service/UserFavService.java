package com.orangelabs.tuya2capi.tuya2cApi.business.fav.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.orangelabs.tuya2capi.tuya2cApi.business.fav.mapping.OrangeUserFavMapper;
import com.orangelabs.tuya2capi.tuya2cApi.business.fav.model.OrangeUserFav;
import com.orangelabs.tuya2capi.tuya2cApi.business.fav.req.FavRequest;
import com.orangelabs.tuya2capi.tuya2cApi.business.fav.resp.FavListResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.fav.resp.UserIsFavResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.model.OrangeProduct;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ProductResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.service.ProductService;
import com.orangelabs.tuya2capi.tuya2cApi.utils.ExcelExportUtil;

@Service
public class UserFavService {
	
    private Logger log = Logger.getLogger(UserFavService.class);
	
	@Autowired
	private OrangeUserFavMapper orangeUserFavMapper;
	
	@Autowired
	private ProductService productService;
	
	public Map<String, Object> getUserFavList(String userId) throws Exception {
		log.info("to get the user fav list, userid " + userId);
		Map<String, Object> map = new HashMap<>();
		List<FavListResp> list = getFavRespList(userId);
		map.put("list", list);
		map.put("message", "User favourite lists");
		
		return map;
	}
	
	private List<FavListResp> getFavRespList(String userId) throws Exception{
		List<FavListResp> result = new ArrayList<>();
		
		List<OrangeUserFav> listfromdb = getUserFavFromDB(userId);
		if (listfromdb != null && listfromdb.size() > 0) {
			for (OrangeUserFav ouf : listfromdb) {
				FavListResp singelone = getSingelFavListResp(ouf);
				result.add(singelone);
			}
		} else {
			log.info(userId + " no fav data....");
		}
		
		return result;
	}
	
	private List<OrangeUserFav> getUserFavFromDB(String userId) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("orangeUserId", Long.valueOf(userId));
		
		List<OrangeUserFav> list = orangeUserFavMapper.selectFavByCondition(map);
		return list;
	}
	
	private FavListResp getSingelFavListResp(OrangeUserFav fav) throws Exception {
		
		OrangeProduct op = productService.getProductById(fav.getProductId());
		ProductResp productId = productService.getSingelProductResp(op);
		
		
		FavListResp resp = new FavListResp();
		resp.set_id(fav.getFavId().toString());
		resp.setUserId(fav.getOrangeUserId().toString());
		resp.setProductId(productId);
		
		return resp;
	}
	
	
	public Map<String, Object> getUserIsFavThisProduct(String productId) throws Exception {
		log.info("to check whether the user fav the product, id " + productId);
		Map<String, Object> map = new HashMap<>();
		List<UserIsFavResp> list = queryUserIsFavFromDb(productId);
		map.put("list", list);
		map.put("message", "User is favourite this product");
		
		return map;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> addFavToList(FavRequest request) throws Exception {
		log.info("add product to list " + JSONObject.toJSONString(request));
		Map<String, Object> map = new HashMap<>();
		
		String useid = request.getUserId();
		String productid = request.getProductId();
		
		OrangeUserFav fav = new OrangeUserFav();
		fav.setProductId(Long.valueOf(productid));
		fav.setOrangeUserId(Long.valueOf(useid));
		fav.setCreateTime(new Date());
		fav.setUpdateTime(new Date());
		
		orangeUserFavMapper.insert(fav);
		
		UserIsFavResp resp = getSingelUserIsFav(fav);
		
		map.put("list", resp);
		
		
		return map;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> deleteFavToList(String favId) throws Exception {
		log.info("delete from the list, favId" + favId);
		Map<String, Object> map = new HashMap<>();
		orangeUserFavMapper.deleteByPrimaryKey(Long.valueOf(favId));
		map.put("message", "Favourite product delete");
		return map;
	}
	
	private List<UserIsFavResp> queryUserIsFavFromDb(String productId) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("productId", Long.valueOf(productId));
		List<OrangeUserFav> list = orangeUserFavMapper.selectFavByCondition(map);
		
		List<UserIsFavResp> result = getUserIsFavResp(list);
		
		return result;
	}
	
	private List<UserIsFavResp> getUserIsFavResp(List<OrangeUserFav> favs) {
		
		List<UserIsFavResp> result = new ArrayList<>();
		
		if (favs != null && favs.size() > 0) {
			for (OrangeUserFav fav: favs) {
				UserIsFavResp resp = getSingelUserIsFav(fav);
				result.add(resp);
			}
		}
		
		return result;
	}
	
	
	private UserIsFavResp getSingelUserIsFav(OrangeUserFav fav) {
		UserIsFavResp resp = new UserIsFavResp();
		resp.set_id(fav.getFavId().toString());
		resp.setProductId(fav.getProductId().toString());
		resp.setUserId(fav.getOrangeUserId().toString());
		
		return resp;
	}
	
	public HSSFWorkbook exportFAVdataToExcel(List<String> ids, String fileName) throws Exception{
		log.info("to export file, ids " + JSONObject.toJSONString(ids));
		
		HSSFWorkbook wb = null;
		
		List<ProductResp> resp = productService.getFavProducts(ids);
		if (resp != null && resp.size() > 0) {
			
			String[] title = {"Main Image","Product Name", "Product Model Name", "Certification","Protocol","Connect Voice Platform","Product Link", "Orange Recommendation", "Orange Validation"};
			
			String sheetName = "sheet1";
			
			wb = ExcelExportUtil.getHSSFWorkbook(sheetName, title, resp, null);
		}
		
		return wb;
	}
	
//	public XSSFWorkbook exportFAVdataToExcel2(List<String> ids, String fileName) throws Exception{
//		log.info("to export file, ids " + JSONObject.toJSONString(ids));
//		
//		XSSFWorkbook wb = null;
//		
//		List<ProductResp> resp = productService.getFavProducts(ids);
//		if (resp != null && resp.size() > 0) {
//			
//			String[] title = {"Main Image","Product Name", "Product Model Name", "Certification","Protocol","Connect Voice Platform","Product Link", "Orange Recommendation", "Orange Validation"};
//			
//			String sheetName = "sheet1";
//			
//			wb = ExcelExportUtil.getXSSFWorkbook(sheetName, title, resp, null);
//		}
//		
//		return wb;
//	}

}
