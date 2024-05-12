package com.shoesstore.service;

import java.util.List;
import java.util.Optional;

import com.shoesstore.model.Category;


public interface CategoryService {
	List<Category> getAllCategories();
	Optional<Category>  getCategoryById(int categoryId);
   
}
