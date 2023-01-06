package com.orangelabs.tuya2capi.tuya2cApi.business.products.req;

import java.util.List;

public class ParamReq {
	
	private String name;
	
	private List<String> vals;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getVals() {
		return vals;
	}

	public void setVals(List<String> vals) {
		this.vals = vals;
	}
}
