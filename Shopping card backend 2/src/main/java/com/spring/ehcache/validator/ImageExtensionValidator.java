package com.spring.ehcache.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.spring.ehcache.common.CommonConstantValue;
import com.spring.ehcache.entity.ImageExtension;
import com.spring.ehcache.repository.ImageExtensionRepository;

@Component
public class ImageExtensionValidator {
	private Logger logger;
	private ImageExtensionRepository imageExtensionRepository;

	public ImageExtensionValidator(ImageExtensionRepository imageExtensionRepository) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.imageExtensionRepository = imageExtensionRepository;
	}

	public boolean validateDocumentExtension(MultipartFile file) {
		String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		logger.info("Start Validate document extension {}", extension);
		List<ImageExtension> docTypeList = this.imageExtensionRepository.findAll();
		List<String> docList = docTypeList.stream().map(t -> t.getExtention().toUpperCase()).collect(Collectors.toList());
		logger.info("End Validate document extension {} -> {}", extension, CommonConstantValue.STATUS_SUCCESS);
		return docList.contains(extension.toUpperCase());
	}
}
