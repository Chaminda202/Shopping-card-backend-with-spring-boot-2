package com.spring.ehcache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@PropertySource("classpath:app.properties")
@Data
public class AppErrorConfig {
	@Value("${error.not-exit}")
	private String notExist;

	@Value("${error.create}")
	private String create;

	@Value("${error.update}")
	private String update;

	@Value("${error.by-id}")
	private String byId;
	
	@Value("${error.by-name-occu}")
	private String byNameOccu;
	
	@Value("${error.all}")
	private String all;
	
	@Value("${error.all-with-pagin}")
	private String allWithPagin;

	@Value("${error.delete}")
	private String delete;

	@Value("${error.validation}")
	private String validation;
	
	@Value("${error.type-matching}")
	private String typeMatching;
	
	@Value("${error.invalid-username-password}")
	private String invalidUsernamePassword;
	
	@Value("${error.create-image-location}")
	private String createImageLocation;
	
	@Value("${error.create-image}")
	private String createImage;
	
	@Value("${error.invalid-image-extension}")
	private String invalidImageExtension;
	
	@Value("${error.create-product}")
	private String createProduct;
	
	@Value("${error.product-not-exit}")
	private String productNotExist;
	
	@Value("${error.product-by-id}")
	private String productById;
	
	@Value("${error.product-update}")
	private String productUpdate;
	
	@Value("${error.product-all}")
	private String productAll;
	
	@Value("${error.product-all-with-pagin}")
	private String productAllWithPagin;
	
	@Value("${error.product-delete}")
	private String productDelete;
	
	@Value("${error.product-by-seller-id}")
	private String productsBySellerId;
	
	@Value("${error.product-by-seller-id-with-pagin}")
	private String productsBySellerIdWithPagin;
	
	@Value("${error.delete-existing-image}")
	private String deleteExistingImage;
	
	@Value("${product-image-pattern}")
	private String productPattern;
	
	@Value("${product-image-location}")
	private String productLocation;

	@Value("${product-image-retrive-path}")
	private String productImageRetrivePath;
}
