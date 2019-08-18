package com.spring.ehcache.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
	private String name;
    private String description;
    private String address;
    private BigDecimal unitPrice;
    private String category;
    private Integer quantity;
    private MultipartFile productImage;
    private String terms;
    private Integer sellerId;
    private String sellerName;
    private String actualPath;
    private String relativePath;
    private String changeImageName;
    private String actualImageName;
}

