package com.spring.ehcache.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ehcache.common.CommonConstantValue;
import com.spring.ehcache.common.GsonUtil;
import com.spring.ehcache.config.AppErrorConfig;
import com.spring.ehcache.request.OrderDetailsRequest;
import com.spring.ehcache.service.OrderDetailsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/orders")
public class OrderDetailsController {
	private Logger logger;
	private OrderDetailsService orderDetailsService;
	private AppErrorConfig appErrorConfig;
	
	public OrderDetailsController(OrderDetailsService orderDetailsService, AppErrorConfig appErrorConfig) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.orderDetailsService = orderDetailsService;
		this.appErrorConfig = appErrorConfig;

	}
	
	@ApiOperation(value = "Add a order")
	@PostMapping
	public Map<String, Object> save(@Valid @RequestBody OrderDetailsRequest request) {
		logger.info("Start add order controller {}", GsonUtil.getToString(request, OrderDetailsRequest.class));
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.orderDetailsService.save(request));
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, this.appErrorConfig.getCreateOrder());
			logger.error("Add user {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}

}
