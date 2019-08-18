package com.spring.ehcache.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable{
	private static final long serialVersionUID = -5086794710081511649L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "product_id")
	private Integer productId;
	@Column(name = "seller_id")
	private Integer sellerId;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "ordered_quantity")
	private Integer orderedQuantity;
	@Column(name = "unit_price")
	private BigDecimal unitPrice;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id", nullable = false)
	private OrderDetail orderDetail;
	
}
