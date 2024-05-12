package com.shoesstore.service;

import java.util.List;
import java.util.Optional;

import com.shoesstore.model.Brand;

public interface BrandService {
	List<Brand> getAllBrands();

	Optional<Brand> getBrandById(int brandId);
}
