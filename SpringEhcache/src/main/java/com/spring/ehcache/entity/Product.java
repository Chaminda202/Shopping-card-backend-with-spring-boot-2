package com.spring.ehcache.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable{
	private static final long serialVersionUID = 6667006103079897401L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
    private String description;
	@Column(name = "address")
    private String address;
	@Column(name = "unit_price")
    private BigDecimal unitPrice;
	@Column(name = "category")
    private String category;
	@Column(name = "quantity")
    private Integer quantity;
	@Column(name = "relative_path")
    private String relativePath;
	@Column(name = "actual_path")
    private String actualPath;
	@Column(name = "terms")
    private String terms;
	@Column(name = "seller_id")
    private Integer sellerId;
	@Column(name = "seller_name")
    private String sellerName;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;
	@Column(name = "change_image_name")
    private String changeImageName;
	@Column(name = "actual_image_name")
    private String actualImageName;
}
