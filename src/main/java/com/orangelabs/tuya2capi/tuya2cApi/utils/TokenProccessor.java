package com.orangelabs.tuya2capi.tuya2cApi.utils;

import java.util.UUID;

public class TokenProccessor {

	private TokenProccessor() {
	};

	private static final TokenProccessor instance = new TokenProccessor();

	public static TokenProccessor getInstance() {
		return instance;
	}

	public String makeToken() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
