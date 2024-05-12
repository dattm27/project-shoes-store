package com.shoesstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoesstore.model.Brand;
import com.shoesstore.repository.BrandRepository;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepository brandRepository;
	@Override
	public List<Brand> getAllBrands() {
		
		return  brandRepository.findAll();
	}
	@Override
	public Optional<Brand> getBrandById(int brandId) {
		
		return brandRepository.findById(brandId);
	}

}
