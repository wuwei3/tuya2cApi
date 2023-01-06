package com.orangelabs.tuya2capi.tuya2cApi;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSONObject;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ParamsResp;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.service.ProductService;

@SpringBootTest
class Tuya2cApiApplicationTests {
	
	@Autowired
	ProductService productService;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testzhuanhuan() throws Exception{
//		Map<String, Object> list = productService.getAllProducts("all");
//		 
//		System.out.println(JSONObject.toJSONString(list));
		
	}

}
