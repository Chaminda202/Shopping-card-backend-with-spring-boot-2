package com.spring.ehcache.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.ehcache.common.CommonConstantValue;
import com.spring.ehcache.config.AppErrorConfig;
import com.spring.ehcache.dto.ProductDTO;
import com.spring.ehcache.exception.ApplicationException;
import com.spring.ehcache.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/products")
public class ProductController {
	private Logger logger;
	private ProductService productService;
	private AppErrorConfig appErrorConfig;
	
	public ProductController(ProductService productService, AppErrorConfig appErrorConfig) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.productService = productService;
		this.appErrorConfig = appErrorConfig;

	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> save(@RequestParam("image") MultipartFile image, @RequestParam("name") String name,
			@RequestParam("description") String description, @RequestParam("address") String address,
			@RequestParam("unitPrice") BigDecimal unitPrice, @RequestParam("category") String category,
			@RequestParam("quantity") Integer quantity, @RequestParam("terms") String terms,
			@RequestParam("sellerId") Integer sellerId, @RequestParam("sellerName") String sellerName) {
		logger.info("Start product saving controller {} -> {}", name, sellerName);
		Map<String, Object> response = new HashMap<>();
		try{
			ProductDTO product = ProductDTO.builder()
					.productImage(image)
					.name(name)
					.description(description)
					.category(category)
					.address(address)
					.unitPrice(unitPrice)
					.category(category)
					.sellerId(sellerId)
					.sellerName(sellerName)
					.quantity(quantity)
					.terms(terms)
					.build();
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.productService.save(product));
			logger.info("Product saving controller {} -> {}",name, CommonConstantValue.STATUS_SUCCESS);
		}catch (ApplicationException e) {
			logger.error("Product saving controller {} -> {} -> {}", name, CommonConstantValue.STATUS_FAILED, e.getMessage());
			response.put(CommonConstantValue.STATUS, CommonConstantValue.STATUS_FAILED);
			response.put(CommonConstantValue.MESSAGE, e.getMessage());
		}catch (Exception ex) {
			logger.error("Product saving controller {} -> {} -> {}", name, CommonConstantValue.STATUS_FAILED, ex.getMessage());
			response.put(CommonConstantValue.STATUS, CommonConstantValue.STATUS_FAILED);
			response.put(CommonConstantValue.MESSAGE,this.appErrorConfig.getCreateProduct());
		}
		return response;
	}
	
	@ApiOperation(value = "Get a product by Id")
	@GetMapping(value = "/{id}")
	public Map<String, Object> getOne(
			@ApiParam(required = true, name = "id", value = "Id cannot be missing or empty") @PathVariable Integer id) {
		logger.info("Starts get product by id {}", id);
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.productService.getById(id));
			logger.info("Get product by id {} -> {}",id, CommonConstantValue.STATUS_SUCCESS);
		} catch (ApplicationException e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, e.getMessage());
			logger.error("Get product by id {} -> {} -> {}", id, CommonConstantValue.STATUS_FAILED, e.getMessage());
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, this.appErrorConfig.getProductById());
			logger.error("Get product by id {} -> {} -> {}", id, CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
	
	@ApiOperation(value = "View a list of products with pagination")
	@GetMapping(value = "/allWtPagin")
	public Map<String, Object> getAllWithPagin(
			@ApiParam(required = true, name = "page", value = "Page cannot be missing or empty") @RequestParam("page") int page,
			@ApiParam(required = true, name = "size", value = "Size cannot be missing or empty") @RequestParam("size") int size) {
		logger.info("Start products by pagin controller {} -> {}", page, size);
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.productService.getAllWithPagin(page, size));
			logger.info("Get all products {}", CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, this.appErrorConfig.getProductAllWithPagin());
			logger.error("Get users {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
	
	@ApiOperation(value = "View list of products")
	@GetMapping
	public Map<String, Object> getAll() {
		logger.info("Starts get products list");
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.productService.getAll());
			logger.info("Get products list {}", CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, this.appErrorConfig.getProductAll());
			logger.error("Get products list {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> update(@RequestParam("image") MultipartFile image, @RequestParam("name") String name,
			@RequestParam("description") String description, @RequestParam("address") String address,
			@RequestParam("unitPrice") BigDecimal unitPrice, @RequestParam("category") String category,
			@RequestParam("quantity") Integer quantity, @RequestParam("terms") String terms,
			@RequestParam("sellerId") Integer sellerId, @RequestParam("sellerName") String sellerName,
			@RequestParam("productId") Integer productId) {
		logger.info("Start product updating controller {} -> {}", name, sellerName);
		Map<String, Object> response = new HashMap<>();
		try{
			ProductDTO product = ProductDTO.builder()
					.productImage(image)
					.name(name)
					.description(description)
					.category(category)
					.address(address)
					.unitPrice(unitPrice)
					.category(category)
					.sellerId(sellerId)
					.sellerName(sellerName)
					.quantity(quantity)
					.terms(terms)
					.build();
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.productService.update(product, productId));
			logger.info("Product updating controller {} -> {}", name, CommonConstantValue.STATUS_SUCCESS);
		}catch (ApplicationException e) {
			logger.error("Product updating controller {} -> {} -> {}", name, CommonConstantValue.STATUS_FAILED, e.getMessage());
			response.put(CommonConstantValue.STATUS, CommonConstantValue.STATUS_FAILED);
			response.put(CommonConstantValue.MESSAGE, e.getMessage());
		}catch (Exception ex) {
			logger.error("Product updating controller{} -> {} -> {}", name, CommonConstantValue.STATUS_FAILED, ex.getMessage());
			response.put(CommonConstantValue.STATUS, CommonConstantValue.STATUS_FAILED);
			response.put(CommonConstantValue.MESSAGE,this.appErrorConfig.getProductUpdate());
		}
		return response;
	}
	
	@ApiOperation(value = "View list of products by seller id")
	@GetMapping(value = "/seller/{id}")
	public Map<String, Object> getProductBySelllerId(
			@ApiParam(required = true, name = "id", value = "Id cannot be missing or empty") @PathVariable Integer id) {
		logger.info("Starts get product by seller id {}", id);
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.productService.getAllBySellerId(id));
			logger.info("Get product by id {} -> {}", id, CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, this.appErrorConfig.getProductsBySellerId());
			logger.error("Get product by id {} -> {} -> {}", id, CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
	
	@ApiOperation(value = "View list of products by seller id with pagin")
	@GetMapping(value = "/seller/pagin/{id}")
	public Map<String, Object> getProductBySelllerIdWithPagin(
			@ApiParam(required = true, name = "page", value = "Page cannot be missing or empty") @RequestParam("page") int page,
			@ApiParam(required = true, name = "size", value = "Size cannot be missing or empty") @RequestParam("size") int size,
			@ApiParam(required = true, name = "id", value = "Id cannot be missing or empty") @PathVariable Integer id) {
		logger.info("Starts get product by seller id {}", id);
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.productService.getAllBySellerIdWithPagin(id, page, size));
			logger.info("Get product by id {} -> {}", id, CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, this.appErrorConfig.getProductsBySellerIdWithPagin());
			logger.error("Get product by id {} -> {} -> {}", id, CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
	
	@ApiOperation(value = "View list of products group by category")
	@GetMapping(value = "/display")
	public Map<String, Object> getAllGroupByCategory() {
		logger.info("Starts get products list group by category");
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.DATA, this.productService.getAllGroupByCategory());
			logger.info("Get products list group by category {}", CommonConstantValue.STATUS_SUCCESS);
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, this.appErrorConfig.getProductAll());
			logger.error("Get products list group by category {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}

	
	@ApiOperation(value = "Delete a product")
	@DeleteMapping(value = "/{id}")
	public Map<String, Object> delete(
			@ApiParam(required = true, name = "id", value = "Id cannot be missing or empty") @PathVariable Integer id) {
		logger.info("Starts delete product by id {}", id);
		Map<String, Object> response = new HashMap<>();
		try {
			this.productService.delete(id);
			response.put(CommonConstantValue.STATUS, true);
			response.put(CommonConstantValue.MESSAGE, "User delete successfully");
		} catch (ApplicationException e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, e.getMessage());
			logger.error("Delete product by id {} -> {} -> {}",id, CommonConstantValue.STATUS_FAILED, e.getMessage());
		} catch (Exception e) {
			response.put(CommonConstantValue.STATUS, false);
			response.put(CommonConstantValue.MESSAGE, this.appErrorConfig.getProductDelete());
			logger.error("Delete product by id {} -> {} -> {}",id, CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return response;
	}
}
