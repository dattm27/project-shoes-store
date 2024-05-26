package com.shoesstore.service;
import java.util.List;

import com.shoesstore.model.ProductSize;
public interface ProductSizeService {
	List<String> getAllSizes();
	ProductSize getSizeById(int id);
}
