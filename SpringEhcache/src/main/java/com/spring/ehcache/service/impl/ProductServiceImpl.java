package com.spring.ehcache.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.spring.ehcache.common.CommonConstantValue;
import com.spring.ehcache.config.AppErrorConfig;
import com.spring.ehcache.dto.ProductDTO;
import com.spring.ehcache.entity.Product;
import com.spring.ehcache.exception.ApplicationException;
import com.spring.ehcache.repository.ProductRepository;
import com.spring.ehcache.response.ProductDisplayResponse;
import com.spring.ehcache.response.ProductPaginResponse;
import com.spring.ehcache.response.ProductResponse;
import com.spring.ehcache.service.ProductService;
import com.spring.ehcache.validator.ImageExtensionValidator;

@Service
public class ProductServiceImpl implements ProductService {
	private Logger logger;
	private ProductRepository productRepository;
	private ImageExtensionValidator imageExtensionValidator;
	private AppErrorConfig appErrorConfig;
	@Value("${application.context.path}")
	private String contextPath;
	
	public ProductServiceImpl(ProductRepository productRepository, ImageExtensionValidator imageExtensionValidator,
			AppErrorConfig appErrorConfig) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.imageExtensionValidator = imageExtensionValidator;
		this.productRepository = productRepository;
		this.appErrorConfig = appErrorConfig;
	}

	@Override
	public ProductResponse save(ProductDTO request) throws ApplicationException {
		logger.info("Save product service {} -> {}", request.getName(), request.getSellerName());
		if(imageExtensionValidator.validateDocumentExtension(request.getProductImage())) {
			createDirectory(this.appErrorConfig.getProductLocation());
			// Original file name
			String originalFileName = StringUtils.cleanPath(request.getProductImage().getOriginalFilename());
			request.setActualImageName(originalFileName);
			logger.info("Save product original file name {}", originalFileName);
			String changeImageName = changeImageName(request.getProductImage());
			logger.info("Save product change image name {}", changeImageName);
			request.setChangeImageName(changeImageName);
			String savingFilePath = this.appErrorConfig.getProductLocation() + changeImageName;
			request.setActualPath(savingFilePath);
			logger.info("Save product saving image path {}", savingFilePath);
			String relativeImagePath = this.appErrorConfig.getProductImageRetrivePath() + changeImageName;
			request.setRelativePath(relativeImagePath);
			logger.info("Save product relative image path {}", relativeImagePath);
			storeImageStorage(request, savingFilePath);
			Product createProduct = new Product();
			Product product = this.productRepository.save(buildEntity(createProduct, request));
			return buildResponse(product);
		}
		throw new ApplicationException(this.appErrorConfig.getInvalidImageExtension());
	}

	@Override
	public ProductResponse update(ProductDTO request, Integer id) throws ApplicationException {
		logger.info("Update product service {} -> {}", request.getName(), request.getSellerName());
		Optional<Product> optionalProduct = this.productRepository.findById(id);
		if(optionalProduct.isPresent()) {
			if(imageExtensionValidator.validateDocumentExtension(request.getProductImage())) {
				if(deleteExistingFile(optionalProduct.get().getActualPath())) {
					storeImageStorage(request, optionalProduct.get().getActualPath());
					Product updateProduct = this.productRepository.save(buildEntity(optionalProduct.get(), request));
					// Original file name
					String originalFileName = StringUtils.cleanPath(request.getProductImage().getOriginalFilename());
					request.setActualImageName(originalFileName);
					return buildResponse(updateProduct);
				}
				throw new ApplicationException(this.appErrorConfig.getDeleteExistingImage());
			}
			throw new ApplicationException(this.appErrorConfig.getInvalidImageExtension());
		}
		throw new ApplicationException(this.appErrorConfig.getProductById());
	}

	@Override
	public ProductPaginResponse getAllWithPagin(int page, int size) {
		List<ProductResponse> responseList = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> data = this.productRepository.findAll(pageable);
		data.getContent().forEach(item -> responseList.add(buildResponse(item)));
		return ProductPaginResponse.builder()
				.responseList(responseList)
				.pageNumber(pageable.getPageNumber())
				.pageSize(pageable.getPageSize())
				.totalRecords(data.getTotalElements())
				.build();
	}

	@Override
	public List<ProductResponse> getAll() {
		List<ProductResponse> responseList = new ArrayList<>();
		List<Product> data = this.productRepository.findAll();
		data.forEach(item -> responseList.add(buildResponse(item)));
		return responseList;
	}

	@Override
	public ProductResponse getById(Integer id) throws ApplicationException {
		Optional<Product> optionalProduct = this.productRepository.findById(id);
		if(optionalProduct.isPresent()) {
			return buildResponse(optionalProduct.get());
		}
		throw new ApplicationException(this.appErrorConfig.getProductById());
	}

	@Override
	public void delete(Integer id) throws ApplicationException {
		Optional<Product> optionalProduct = this.productRepository.findById(id);
		if(optionalProduct.isPresent()) {
			if(deleteExistingFile(optionalProduct.get().getActualPath())) {
				this.productRepository.delete(optionalProduct.get());
				return;
			}
			throw new ApplicationException(this.appErrorConfig.getDeleteExistingImage());
		}
		throw new ApplicationException(this.appErrorConfig.getProductById());
	}
	
	@Override
	public ProductPaginResponse getAllBySellerIdWithPagin(Integer id, int page, int size) {
		List<ProductResponse> responseList = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> data = this.productRepository.findAllBySellerId(id, pageable);
		data.getContent().forEach(item -> responseList.add(buildResponse(item)));
		return ProductPaginResponse.builder()
				.responseList(responseList)
				.pageNumber(pageable.getPageNumber())
				.pageSize(pageable.getPageSize())
				.totalRecords(data.getTotalElements())
				.build();
	}

	@Override
	public List<ProductResponse> getAllBySellerId(Integer id) {
		List<ProductResponse> responseList = new ArrayList<>();
		List<Product> data = this.productRepository.findAllBySellerId(id);
		data.forEach(item -> responseList.add(buildResponse(item)));
		return responseList;
	}
	
	@Override
	public List<ProductDisplayResponse> getAllGroupByCategory() {
		List<Product> data = this.productRepository.findAll();
		Map <String, List<ProductResponse>> categoryMap = new HashMap<>();
		List<ProductResponse> list;
		for(Product prod : data) {
			if(categoryMap.containsKey(prod.getCategory())) {
				list = categoryMap.get(prod.getCategory());
				list.add(buildResponse(prod));
			}else {
				list = new ArrayList<>();
				list.add(buildResponse(prod));
				categoryMap.put(prod.getCategory(), list);
			}
		}
		List<ProductDisplayResponse> response = new ArrayList<> ();
		categoryMap.forEach((key,value) -> {
			ProductDisplayResponse res = ProductDisplayResponse.builder()
				.category(key)
				.displayList(value)
				.build();
			response.add(res);
		});
		return response;
	}
		
	private void storeImageStorage(ProductDTO request, String savingFilePath) throws ApplicationException{
		byte[] buf = new byte[1024];
		File files = new File(savingFilePath);
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = request.getProductImage().getInputStream();
			outputStream = new FileOutputStream(files);
			int numRead = 0;
			while ((numRead = inputStream.read(buf)) >= 0) {
				outputStream.write(buf, 0, numRead);
			}
		}catch (Exception e) {
			logger.error("Product image saving {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
			throw new ApplicationException(this.appErrorConfig.getCreateImage());
		}finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Close input stream {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
				}
	         }
	         if (outputStream != null) {
	        	 try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Close output stream {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
				}
	         }
		}	
	}
	
	private boolean deleteExistingFile(String filePath) {
		try {
			File file = new File(filePath);
			return file.delete();
		} catch (Exception e) {
			logger.error("Existing image file delete {} -> {}", CommonConstantValue.STATUS_FAILED, e.getMessage());
		}
		return false;
	}
	
	private void createDirectory(String filePath) throws ApplicationException {
		File directory = new File(String.valueOf(filePath));
		if (!directory.exists()) {
			try {
				Files.createDirectories(Paths.get(filePath));
			} catch (IOException e) {
				logger.error("Create image file location {} -> {} -> {}", filePath,
						CommonConstantValue.STATUS_FAILED, e.getMessage());
				throw new ApplicationException(this.appErrorConfig.getCreateImageLocation());
			}
		}
	}
	
	 private String changeImageName(MultipartFile file){
	    	StringBuilder builder = new StringBuilder();
	    		builder.append(StringUtils.stripFilenameExtension(file.getOriginalFilename()));
	    		builder.append("_");
	    		builder.append(Long.toString(System.currentTimeMillis()));
	    		builder.append(".");
	    		builder.append(StringUtils.getFilenameExtension(file.getOriginalFilename()));
	    	return builder.toString();
	    }

	
	private Product buildEntity(Product product, ProductDTO request) {
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setAddress(request.getAddress());
		product.setCategory(request.getCategory());
		product.setTerms(request.getTerms());
		product.setQuantity(request.getQuantity());
		product.setUnitPrice(request.getUnitPrice());
		product.setSellerId(request.getSellerId());
		product.setSellerName(request.getSellerName());
		product.setRelativePath(request.getRelativePath() != null ? request.getRelativePath(): product.getRelativePath());
		product.setActualPath(request.getActualPath());
		product.setActualImageName(request.getActualImageName() != null ? request.getActualImageName() : product.getActualImageName());
		product.setChangeImageName(request.getChangeImageName() != null ? request.getChangeImageName() :  product.getChangeImageName());
		return product;
	}
	
	private ProductResponse buildResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.address(product.getAddress())
				.category(product.getCategory())
				.terms(product.getTerms())
				.quantity(product.getQuantity())
				.unitPrice(product.getUnitPrice())
				.sellerId(product.getSellerId())
				.sellerName(product.getSellerName())
				.imagePath(contextPath + product.getRelativePath())
				.build();
	}
}
