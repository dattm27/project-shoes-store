package com.shoesstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoesstore.model.ProductSize;
import com.shoesstore.repository.ProductSizeRepository;
@Service
public class ProductSizeServiceImpl implements ProductSizeService {
	@Autowired
	private ProductSizeRepository productSizeRepository;
	@Override
	public List<String> getAllSizes() {	
		return productSizeRepository.findDistinctSizes();
	}
	@Override
	public ProductSize getSizeById(int id) {
		
		return productSizeRepository.findById(id).get();
	}

}
