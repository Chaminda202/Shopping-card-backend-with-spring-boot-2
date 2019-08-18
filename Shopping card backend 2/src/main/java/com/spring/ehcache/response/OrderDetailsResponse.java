package com.spring.ehcache.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class OrderDetailsResponse {
	@JsonProperty("order_id")
	private Integer id;
	@JsonProperty("customer_id")
	private Integer customerId;
	@JsonProperty("customer_name")
	private String customerName;
	@JsonProperty("delivery_address")
	private String deliveryAddress;
	@JsonProperty("email")
	private String email;
	@JsonProperty("order_items")
	private List<OrderItemResponse> orderItems;
}
