package com.shoesstore.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoesstore.model.Brand;
import com.shoesstore.model.Category;
import com.shoesstore.model.CustomUserDetails;
import com.shoesstore.model.Order;
import com.shoesstore.model.Product;
import com.shoesstore.service.BrandService;
import com.shoesstore.service.CategoryService;
import com.shoesstore.service.OrderService;
import com.shoesstore.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private OrderService orderService;

	@PostMapping("/layout")
	public String showLayout(Model model) {
		// Lấy thông tin người dùng từ session để lấy thông tin công ty
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		model.addAttribute("username", currentUser.getFullName());

		// lấy danh sách hàng hoá đưa vào

		return "admin/layout";
	}

	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		// Lấy thông tin người dùng từ session để lấy thông tin công ty
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		model.addAttribute("username", currentUser.getFullName());

		// lấy danh sách hàng hoá đưa vào

		return "admin/layout";
	}

	@GetMapping("/products")
	public String redirectToDashboard() {
		// Lấy thông tin người dùng từ session để lấy thông tin công ty
	
		return "redirect:/admin/dashboard";
	}

	@PostMapping("/products")
	public String ProductManage() {
		// Lấy thông tin người dùng từ session để lấy thông tin công ty
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		// model.addAttribute("username", currentUser.getFullName());

		// lấy danh sách hàng hoá đưa vào

		return "admin/products";
	};

	@PostMapping("/products/data")
	@ResponseBody
	public List<Product> getProductData(@RequestParam(name = "query[status]", required = false) String status,
			@RequestParam(name = "query[]", required = false) String name) {

		return productService.getProducts(status, name); // Trả về danh sách sản phẩm từ ProductService
	}

	//trả về quản lý danh sách đơn hàng của khách
	@PostMapping("/orders")
	public String OrderManage() {
		// Lấy thông tin người dùng từ session để lấy thông tin công ty
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		return "admin/orders";
	}
	@GetMapping("/orders") 
	public String ShowOrderManage() {
		return "redirect:/admin/dashboard";
	}
	//trả về dữ liệu danh sách các đơn hàng
	@PostMapping("/orders/data")
	@ResponseBody
	public List<Order> getOrderData(@RequestParam(name = "query[status]", required = false) String status,
			@RequestParam(name = "query[]", required = false) String name) {
		
		return orderService.getAllOrders();// Trả về danh sách đơn từ Order Service 
	}
}
