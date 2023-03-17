package com.orangelabs.tuya2capi.tuya2cApi.business.fav.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.BaseResponse;
import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.ResultEnums;
import com.orangelabs.tuya2capi.tuya2cApi.business.fav.req.ExportExcelReq;
import com.orangelabs.tuya2capi.tuya2cApi.business.fav.req.FavRequest;
import com.orangelabs.tuya2capi.tuya2cApi.business.fav.service.UserFavService;
import com.orangelabs.tuya2capi.tuya2cApi.exception.BussinessException;
import com.orangelabs.tuya2capi.tuya2cApi.utils.DateUtil;

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
	
	@RequestMapping(value = "/fav/getSelectFav", method = { RequestMethod.POST })
	@ResponseBody
	public void getSelectFav(@RequestBody ExportExcelReq req, HttpServletResponse response) throws Exception {
		log.info("get select fav");

		String riqi = DateUtil.getDate(DateUtil.datePattern, new Date());
		String fileName = "OrangeSmartHome-Favourites-" + riqi +"-" + System.currentTimeMillis() +  ".xls";
		
		List<String> idList = req.getProductIds();
		HSSFWorkbook wb = userFavService.exportFAVdataToExcel(idList, fileName);
		//XSSFWorkbook wb = userFavService.exportFAVdataToExcel2(idList, fileName);
		
		if (wb != null) {
			try {
	            this.setResponseHeader(response, fileName);
	            OutputStream os = response.getOutputStream();
	            wb.write(os);
	            os.flush();
	            os.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		} else {
			throw new BussinessException(ResultEnums.BUSSINESS_ERROR,  " export failed!!!!");
		}
	}
	
	public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //response.setContentType("application/vnd.ms-excel;charset=ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
