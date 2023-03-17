package com.orangelabs.tuya2capi.tuya2cApi.business.fav.req;

import java.util.List;

public class ExportExcelReq {
	
	private List<String> productIds;

	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}
	
}
