package com.shoesstore.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shoesstore.model.CustomUserDetails;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		// Lấy thông tin người dùng từ session để lấy thông tin công ty
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		model.addAttribute("username", currentUser.getFullName());	
		return "admin/layout";
	}
}
