package com.orangelabs.tuya2capi.tuya2cApi.business.products.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orangelabs.tuya2capi.tuya2cApi.baseresponse.BaseResponse;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.service.UploadProductsExcelService;
import com.orangelabs.tuya2capi.tuya2cApi.utils.ConfigBeanValue;

@BaseResponse
@RestController
public class UploadProductExcel {
	
	private Logger log = Logger.getLogger(UploadProductExcel.class);
	
	@Autowired
    private ConfigBeanValue configBeanValue;
	
	@Autowired
    private UploadProductsExcelService uploadProductsExcelService;
	
	@RequestMapping(value = "/uploadProductFileToServer", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> uploadProductFile(@RequestParam(value = "files", required = false) MultipartFile[] files,
			HttpServletRequest request) throws Exception {
		log.info("upload product excel");
		
		Map<String,Object> map = new HashMap<>();
		String msg = "";
		if (files != null && files.length > 0) {
			MultipartFile upload_file = files[0];
			
			log.info("upload from Upload component  " + upload_file.getOriginalFilename() + "   "
					+ upload_file.getSize());
			String fileStorepath = configBeanValue.save_productExcel_path;
			String imageStorePath = configBeanValue.save_productExcel_productimg;
			try {
				File folder = new File(fileStorepath + File.separator);
				if (!folder.exists()) {
					folder.mkdir();
				}
				
				String filepath = fileStorepath + File.separator + upload_file.getOriginalFilename();
				File storefile = new File(filepath);		
				upload_file.transferTo(storefile);
				
				uploadProductsExcelService.uploadDataToDb(filepath, imageStorePath, upload_file.getOriginalFilename());
				
				msg = "Product data created";
			} catch (IllegalStateException e) {
				e.printStackTrace();
				msg = e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		} else {
			log.info("not recieve anything execl from  Upload component");
			msg = "not recieve anything execl from page";
		}
		map.put("msg", msg);
		return map;
	}

}
