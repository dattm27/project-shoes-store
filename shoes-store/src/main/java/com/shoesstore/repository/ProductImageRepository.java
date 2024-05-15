package com.shoesstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoesstore.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer>{
	
}
