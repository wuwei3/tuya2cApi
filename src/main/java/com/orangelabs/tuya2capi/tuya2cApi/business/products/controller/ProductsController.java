package com.orangelabs.tuya2capi.tuya2cApi.business.products.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.orangelabs.reservation.base.responsebody.BaseResponse;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.req.ProductReq;
import com.orangelabs.tuya2capi.tuya2cApi.business.products.service.ProductService;

@BaseResponse
@RestController
//@RequestMapping("/products")
public class ProductsController {
	
    private Logger log = Logger.getLogger(ProductsController.class);
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/products/createNewProduct", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> createNewProduct(@RequestBody ProductReq request) throws Exception {
		log.info("create one new product");
		productService.createProduct(request);
		return null;
	}
	
	@RequestMapping(value = "/products/createMutilProducts", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> createMutilProducts(@RequestBody List<ProductReq> request) throws Exception {
		log.info("create one new product");
		
		for (ProductReq req:request) {
			productService.createProduct(req);
		}
		return null;
	}
	
	@RequestMapping(value = "/products", method = { RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getAllProductByPath(String q) throws Exception {
		log.info("get all products by path " + q);
		
		Map<String, Object> map = productService.getAllProducts(q);
		
		return map;
	}
	
	@RequestMapping(value = "/products/{id}", method = { RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getProductDetail(@PathVariable String id) throws Exception {
		log.info("get product detail " + id);
		
		Map<String, Object> map = productService.getProductDetail(id);
		
		return map;
	}
	
	@RequestMapping(value = "/product/{id}", method = { RequestMethod.DELETE})
	@ResponseBody
	public Map<String, Object> deleteProduct(@PathVariable String id) throws Exception {
		log.info("to delete the product " + id);
		
		Map<String, Object> map = productService.deleteProduct(id);
		
		return map;
	}
	
	@RequestMapping(value = "/productnameandprice/{id}", method = { RequestMethod.PUT})
	@ResponseBody
	public Map<String, Object> updateNameAndPrice(@PathVariable String id, @RequestBody ProductReq request) throws Exception {
		log.info("to update the product name and price " + id);
		Map<String, Object> map = productService.updateProduct(id, request);
		return map;
	}

}
