package com.orangelabs.tuya2capi.tuya2cApi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigBeanValue {
	
	 @Value("${save.productExcel.path}")
	 public String save_productExcel_path;
	 
	 @Value("${save.productExcel.productimg}")
	 public String save_productExcel_productimg;
}
