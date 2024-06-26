package com.shoesstore.service;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import  com.shoesstore.model.Product;

public interface ProductService {
	List<Product> getProducts(String status, String name);
	
	Product createProduct(Product product);
	
	boolean updateProductStatus(int id, String status);
	boolean deleteProductStatus(int id);

	Product findById(int id);

	void updateProduct(int productId, String productName, double productPrice, String productVersion, int categoryId,
			int brandId, String description, List<MultipartFile> productImages, List<Integer> deletedImages, Map<String, String> sizes);
	//lọc sản phẩm theo nhu cầu của khách
	List<Product> listProducts(Integer category_id, Integer brand_id);
	
	public List<Product> getFilteredProducts(String status, String name, Integer categoryId, Integer brandId, Double minPrice, Double maxPrice, String size) ;

}
