package com.orangelabs.tuya2capi.tuya2cApi.business.fav.resp;

import com.orangelabs.tuya2capi.tuya2cApi.business.products.resp.ProductResp;

public class FavListResp {
	
	private String _id;
	
	private String userId;
	
	private ProductResp productId;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ProductResp getProductId() {
		return productId;
	}

	public void setProductId(ProductResp productId) {
		this.productId = productId;
	}
}
