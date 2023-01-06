package com.orangelabs.tuya2capi.tuya2cApi.filters;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootConfiguration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter{
	
//	@Autowired
//	private RequestParameterInteceptor interceptor;
	
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestParameterInteceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
