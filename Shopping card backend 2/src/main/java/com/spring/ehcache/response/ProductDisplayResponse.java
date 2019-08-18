package com.spring.ehcache.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDisplayResponse {
	@JsonProperty("category")
	private String category;
	@JsonProperty("display_list")
	private List<ProductResponse> displayList;
}
