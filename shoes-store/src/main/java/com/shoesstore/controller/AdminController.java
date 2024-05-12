package com.shoesstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoesstore.model.CustomUserDetails;
import com.shoesstore.model.Product;
import com.shoesstore.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ProductService productService;
	@PostMapping("/layout")
	public String showLayout(Model model) {
		// Lấy thông tin người dùng từ session để lấy thông tin công ty
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		model.addAttribute("username", currentUser.getFullName());
		
		//lấy danh sách hàng hoá đưa vào
		
		return "admin/layout";
	}
	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		// Lấy thông tin người dùng từ session để lấy thông tin công ty
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		model.addAttribute("username", currentUser.getFullName());
		
		//lấy danh sách hàng hoá đưa vào
		
		return "admin/layout";
	}
	@PostMapping("/products")
	public String ProductManage() {
		// Lấy thông tin người dùng từ session để lấy thông tin công ty
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		//model.addAttribute("username", currentUser.getFullName());
		
		//lấy danh sách hàng hoá đưa vào
		
		return "admin/products";
	}
	
	@PostMapping("/products/data")
	@ResponseBody
	public List<Product> getProductData() {
        return productService.getAllProducts(); // Trả về danh sách sản phẩm từ ProductService
    }
}
