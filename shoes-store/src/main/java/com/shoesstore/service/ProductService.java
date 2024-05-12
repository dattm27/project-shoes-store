package com.shoesstore.service;
import java.util.List;

import  com.shoesstore.model.Product;

public interface ProductService {
	List<Product> getAllProducts();

	Product createProduct(Product product);
}
