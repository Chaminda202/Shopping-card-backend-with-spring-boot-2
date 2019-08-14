package com.spring.ehcache.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.ehcache.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	Page<Product> findAllBySellerId(Integer sellerId, Pageable pageable);
	List<Product> findAllBySellerId(Integer sellerId);
}
