package com.shoesstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoesstore.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
	
}
