package com.orangelabs.tuya2capi.tuya2cApi.business.products.resp;

import java.util.ArrayList;
import java.util.List;

import com.orangelabs.tuya2capi.tuya2cApi.business.products.model.OrangeProductParam2v;

public class ParamsExistsResp {
	
	private boolean eixts = false;
	
	private List<OrangeProductParam2v> list = new ArrayList<>();

	public boolean isEixts() {
		return eixts;
	}

	public void setEixts(boolean eixts) {
		this.eixts = eixts;
	}

	public List<OrangeProductParam2v> getList() {
		return list;
	}

	public void setList(List<OrangeProductParam2v> list) {
		this.list = list;
	}
}
