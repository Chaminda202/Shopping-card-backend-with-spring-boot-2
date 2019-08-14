package com.spring.ehcache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceWebConfiguration implements WebMvcConfigurer {
	private AppErrorConfig appErrorConfig;
	
	public ResourceWebConfiguration(AppErrorConfig appErrorConfig) {
		this.appErrorConfig = appErrorConfig;
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler(this.appErrorConfig.getProductPattern())
			.addResourceLocations("file:///" + this.appErrorConfig.getProductLocation());
	}
}