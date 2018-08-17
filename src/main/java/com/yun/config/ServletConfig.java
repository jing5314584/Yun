package com.yun.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yun.servlet.CheckServerUrlServlet;



@Configuration
public class ServletConfig {
	
	/** 
     * 多个servlet就注册多个bean 
     * @return 
     */  
	@Bean
	public ServletRegistrationBean servletAliPayBean(){
		return new ServletRegistrationBean(new CheckServerUrlServlet(), "/checkServer");
	}
}
