package com.orangelabs.tuya2capi.tuya2cApi.business.fav.resp;

public class UserIsFavResp {
	
	private String _id;
	
	private String userId;
	
	private String productId;

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}
