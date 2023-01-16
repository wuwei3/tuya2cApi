package com.orangelabs.tuya2capi.tuya2cApi.business.products.resp;

import java.util.ArrayList;
import java.util.List;

public class ParamsResp {
	
	private String _id;
	
    private String name;
	
	private List<String> vals = new ArrayList<>();

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

	public List<String> getVals() {
		return vals;
	}

	public void setVals(List<String> vals) {
		this.vals = vals;
	}
}
