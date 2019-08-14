package com.spring.ehcache.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
	@JsonProperty("product_id")
	private Integer id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
    private String description;
	@JsonProperty("address")
    private String address;
	@JsonProperty("unit_price")
    private BigDecimal unitPrice;
	@JsonProperty("category")
    private String category;
	@JsonProperty("quantity")
    private Integer quantity;
	@JsonProperty("image_path")
    private String imagePath;
	@JsonProperty("terms")
    private String terms;
	@JsonProperty("seller_id")
    private Integer sellerId;
	@JsonProperty("seller_name")
    private String sellerName;
}
