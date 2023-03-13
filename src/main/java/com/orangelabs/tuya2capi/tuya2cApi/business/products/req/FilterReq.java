package com.orangelabs.tuya2capi.tuya2cApi.business.products.req;

import java.util.HashMap;
import java.util.Map;

public class FilterReq {
	
	private Map<String, Object> filterMap = new HashMap<>();
	
	private String path;

	public Map<String, Object> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, Object> filterMap) {
		this.filterMap = filterMap;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
