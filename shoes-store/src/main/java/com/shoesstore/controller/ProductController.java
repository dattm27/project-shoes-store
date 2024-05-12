package com.shoesstore.controller;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.shoesstore.model.Brand;
import com.shoesstore.model.Category;
import com.shoesstore.model.Product;
import com.shoesstore.service.BrandService;
import com.shoesstore.service.CategoryService;
import com.shoesstore.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseEntity<?> createProduct(@RequestParam("productName") String productName,
	                            @RequestParam("productPrice") double productPrice,
	                            @RequestParam("productVersion") String productVersion,
	                            @RequestParam("productCategory") int categoryId,
	                            @RequestParam("productBrand") int brandId) {
	    // Tạo mới sản phẩm và lưu vào cơ sở dữ liệu
	    Product product = new Product();
	    product.setName(productName);
	    product.setPrice(productPrice);
	    product.setVersion(productVersion);
	    product.setCreatedAt( new Date());
	    product.setUpdatedAt( new Date());
	    product.setStatus("Đang bán");

	 
		// Lấy thông tin về danh mục và thương hiệu từ cơ sở dữ liệu
	    Category category = categoryService.getCategoryById(categoryId).get();
	    Brand brand = brandService.getBrandById(brandId).get();

	    // Gán danh mục và thương hiệu cho sản phẩm
	    product.setCategory(category);
	    product.setBrand(brand);

	    // Lưu sản phẩm vào cơ sở dữ liệu
	    Product savedProduct = productService.createProduct(product);

	    // Chuyển hướng lại trang danh sách sản phẩm
	    return ResponseEntity.ok().body(savedProduct);
	}

}
