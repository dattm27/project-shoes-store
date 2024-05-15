package com.shoesstore.service;
import java.util.List;

import  com.shoesstore.model.Product;

public interface ProductService {
	List<Product> getProducts(String status, String name);
	
	Product createProduct(Product product);
	
	boolean updateProductStatus(int id, String status);
	boolean deleteProductStatus(int id);
}
