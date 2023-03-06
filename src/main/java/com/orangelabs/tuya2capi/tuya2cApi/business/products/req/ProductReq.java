package com.orangelabs.tuya2capi.tuya2cApi.business.products.req;

import java.util.List;

public class ProductReq {
	
	private String name;
	
	private String path;
	
	private List<String> tags;
	
	private String productManufacturer;
	
	private String imgUrl;
	
	private String price;
	
	private String imageBase64;
	
	private List<ParamReq> params;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<ParamReq> getParams() {
		return params;
	}

	public void setParams(List<ParamReq> params) {
		this.params = params;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	public String getProductManufacturer() {
		return productManufacturer;
	}

	public void setProductManufacturer(String productManufacturer) {
		this.productManufacturer = productManufacturer;
	}
}
