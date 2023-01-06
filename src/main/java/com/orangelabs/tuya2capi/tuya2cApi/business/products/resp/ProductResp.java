package com.orangelabs.tuya2capi.tuya2cApi.business.products.resp;

import java.util.List;

public class ProductResp {
	
	private String _id;
	
    private String name;
	
	private String path;
	
	private List<String> tags;
	
	private String imgUrl;
	
	private String price;
	
	private List<ParamsResp> params;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

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

	public List<ParamsResp> getParams() {
		return params;
	}

	public void setParams(List<ParamsResp> params) {
		this.params = params;
	}
}
