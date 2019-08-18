package com.spring.ehcache.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class OrderItemResponse {
	@JsonProperty("item_id")
	private Integer id;
	@JsonProperty("product_id")
	private Integer productId;
	@JsonProperty("seller_id")
	private Integer sellerId;
	@JsonProperty("product_name")
	private String productName;
	@JsonProperty("ordered_quantity")
	private Integer orderedQuantity;
	@JsonProperty("unit_price")
	private BigDecimal unitPrice;
}
