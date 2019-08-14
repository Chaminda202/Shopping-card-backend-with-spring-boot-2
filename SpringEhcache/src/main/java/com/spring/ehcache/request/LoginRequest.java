package com.spring.ehcache.request;

import lombok.Data;

@Data
public class LoginRequest {
	private String name;
	private String password;
}
