package com.spring.ehcache.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.ehcache.entity.OrderDetail;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Integer>{
}
