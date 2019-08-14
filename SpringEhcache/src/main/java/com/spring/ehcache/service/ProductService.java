package com.spring.ehcache.service;

import java.util.List;

import com.spring.ehcache.dto.ProductDTO;
import com.spring.ehcache.exception.ApplicationException;
import com.spring.ehcache.response.ProductDisplayResponse;
import com.spring.ehcache.response.ProductPaginResponse;
import com.spring.ehcache.response.ProductResponse;

public interface ProductService {
	ProductResponse save(ProductDTO request) throws ApplicationException;
	ProductResponse update(ProductDTO request, Integer id) throws ApplicationException;
	ProductPaginResponse getAllWithPagin(int page, int size);
	List<ProductResponse> getAll();
	ProductResponse getById(Integer id) throws ApplicationException;
	void delete(Integer id) throws ApplicationException;
	ProductPaginResponse getAllBySellerIdWithPagin(Integer id, int page, int size);
	List<ProductResponse> getAllBySellerId(Integer id);
	List<ProductDisplayResponse> getAllGroupByCategory();
}
