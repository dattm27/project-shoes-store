package com.shoesstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoesstore.model.Product;
import com.shoesstore.repository.ProductRepository;

@Service
public class ProductSerivceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Override
	public List<Product> getAllProducts() {
		 return productRepository.findAll();
	}
	@Override
	public Product createProduct(Product product) {
		
		return productRepository.save(product);
	}

}
