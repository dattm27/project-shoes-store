package com.shoesstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shoesstore.model.Product;
import com.shoesstore.repository.ProductRepository;

@Service
public class ProductSerivceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	
	@Override
	public List<Product> getProducts(String status, String name) {
		 Specification<Product> spec = Specification.where(null);
		    if (status != null) {
		        spec = spec.and(ProductSpecifications.hasStatus(status));
		    }
		    if (name != null) {
		        spec = spec.and(ProductSpecifications.hasName(name));
		    }
	        return productRepository.findAll(spec);
	};
	@Override
	public Product createProduct(Product product) {
		
		return productRepository.save(product);
	}

}
