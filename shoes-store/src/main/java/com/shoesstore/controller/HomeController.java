package com.shoesstore.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoesstore.model.Brand;
import com.shoesstore.model.Category;
import com.shoesstore.model.Product;
import com.shoesstore.service.BrandService;
import com.shoesstore.service.CategoryService;
import com.shoesstore.service.ProductService;
import com.shoesstore.service.UserService;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductService productService;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/")
	public String showHomePage(Model model) {
		List<Product> productList = productService.getProducts(null, null);
		model.addAttribute("products", productList);
		return "/shopper/index";
	}

	@GetMapping("user-list")
	public String showUsers(Model model) {
		// hien thi danh sach cac user trong he thong
		model.addAttribute("users", userService.getAllUsers());
		return "user-list";

	}

	@GetMapping("signed-in")
	@ResponseBody
	public String checkSignInStatus() {
		// Lấy thông tin xác thực hiện tại
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Kiểm tra xem người dùng đã đăng nhập hay chưa
		if (authentication.isAuthenticated()) {
			// Nếu đã đăng nhập, trả về tên người dùng hoặc thông tin khác
			String username = authentication.getName();
			return username;
		} else {
			// Nếu chưa đăng nhập, trả về một giá trị thể hiện việc không đăng nhập
			return "";
		}
	}

	@GetMapping("/category")
	@ResponseBody
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();

		return ResponseEntity.ok().body(categories);
	}

	@GetMapping("/brands")
	@ResponseBody
	public ResponseEntity<List<Brand>> getAllBrands() {
		List<Brand> brands = brandService.getAllBrands();

		return ResponseEntity.ok().body(brands);
	}

	@GetMapping("/product-listing")
	public String showProductLis(Model model, @RequestParam(name = "category", required = false) Integer category_id,
			@RequestParam(name = "brand", required = false) Integer brand_id) {
		List<Brand> brands = brandService.getAllBrands();
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("brands", brands);
		List<Product> productList = productService.getProducts(null, null);
		model.addAttribute("products", productList);

		return "shopper/product-list";
	}

}
