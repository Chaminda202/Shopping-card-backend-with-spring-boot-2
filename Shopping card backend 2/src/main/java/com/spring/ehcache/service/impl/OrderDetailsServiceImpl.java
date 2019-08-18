package com.spring.ehcache.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.spring.ehcache.config.AppErrorConfig;
import com.spring.ehcache.entity.OrderDetail;
import com.spring.ehcache.entity.OrderItem;
import com.spring.ehcache.exception.ApplicationException;
import com.spring.ehcache.repository.OrderDetailsRepository;
import com.spring.ehcache.request.OrderDetailsRequest;
import com.spring.ehcache.response.OrderDetailsResponse;
import com.spring.ehcache.response.OrderItemResponse;
import com.spring.ehcache.service.OrderDetailsService;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
	private Logger logger;
	private OrderDetailsRepository orderDetailsRepository;
	private AppErrorConfig appErrorConfig;

	public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository, AppErrorConfig appErrorConfig){
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.orderDetailsRepository = orderDetailsRepository;
		this.appErrorConfig = appErrorConfig;
	}
	
	@Override
	public OrderDetailsResponse save(OrderDetailsRequest request){
		logger.info("Save order details service ");
		OrderDetail createOrderDetail = new OrderDetail();	
		return buildResponse(this.orderDetailsRepository.save(buildSaveEntity(createOrderDetail, request)));
	}

	@Override
	public OrderDetailsResponse update(OrderDetailsRequest request, Integer id) throws ApplicationException {
		return null;
	}

	@Override
	public List<OrderDetailsResponse> getAll() {
		return null;
	}

	@Override
	public OrderDetailsResponse getById(Integer id) throws ApplicationException {
		return null;
	}
	
	private OrderDetail buildSaveEntity(OrderDetail orderDetail, OrderDetailsRequest request) {
		orderDetail.setCustomerId(request.getCustomerId());
		orderDetail.setDeliveryAddress(request.getDeliveryAddress());
		orderDetail.setEmail(request.getEmail());
		orderDetail.setCustomerName(request.getCustomerName());
		/*
		 * Set<OrderItem> orderItems = new HashSet<>();
		 * request.getOrderItems().forEach(item -> { OrderItem order = new OrderItem();
		 * order.setProductId(item.getProductId());
		 * order.setProductName(item.getProductName());
		 * order.setUnitPrice(item.getUnitPrice());
		 * order.setOrderedQuantity(item.getOrderedQuantity());
		 * order.setSellerId(item.getSellerId()); order.setOrderDetail(orderDetail);
		 * orderItems.add(order); }); orderDetail.setOrderItems(orderItems);
		 */
		request.getOrderItems().forEach(item -> {
			OrderItem order = new OrderItem();
			order.setProductId(item.getProductId());
			order.setProductName(item.getProductName());
			order.setUnitPrice(item.getUnitPrice());
			order.setOrderedQuantity(item.getOrderedQuantity());
			order.setSellerId(item.getSellerId());
			order.setOrderDetail(orderDetail);
			orderDetail.getOrderItems().add(order);
		});
		return orderDetail;	
	}

	private OrderDetailsResponse buildResponse(OrderDetail orderDetail) {
		List<OrderItemResponse> orderItems = new ArrayList<>();
		orderDetail.getOrderItems().forEach(item -> {
			orderItems.add(OrderItemResponse.builder()
					.id(item.getId())
					.productId(item.getProductId())
					.productName(item.getProductName())
					.unitPrice(item.getUnitPrice())
					.orderedQuantity(item.getOrderedQuantity())
					.build());
		});
		return OrderDetailsResponse.builder()
				.id(orderDetail.getId())
				.customerId(orderDetail.getCustomerId())
				.customerName(orderDetail.getCustomerName())
				.deliveryAddress(orderDetail.getDeliveryAddress())
				.email(orderDetail.getEmail())
				.orderItems(orderItems)
				.build();
	}
}
