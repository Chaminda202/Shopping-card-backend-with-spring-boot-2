package com.spring.ehcache.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5846959394361430195L;
	private String name;
	private String password;
	private Integer age;
	private String occupation;
	private BigDecimal salary;
	private String email;
	private String role;
	private String gender;
	private String string;
}
