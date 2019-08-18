package com.spring.ehcache.service;

import java.util.List;

import com.spring.ehcache.exception.ApplicationException;
import com.spring.ehcache.request.OrderDetailsRequest;
import com.spring.ehcache.response.OrderDetailsResponse;

public interface OrderDetailsService {
	OrderDetailsResponse save(OrderDetailsRequest request);
	OrderDetailsResponse update(OrderDetailsRequest request, Integer id) throws ApplicationException;
	List<OrderDetailsResponse> getAll();
	OrderDetailsResponse getById(Integer id) throws ApplicationException;
}
