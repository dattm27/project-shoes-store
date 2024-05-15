package com.shoesstore.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shoesstore.model.Product;

public interface ProductImageService {
	
	public void saveProductImages(Product product, List<MultipartFile> images) throws IOException;
}
