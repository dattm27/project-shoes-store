package com.shoesstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shoesstore.service.UserService;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String showHomePage(Model model) {
		return "index";
	}
	
	@GetMapping("user-list")
	public String showUsers(Model model) {
		//hien thi danh sach cac user trong he thong
		model.addAttribute("users", userService.getAllUsers());
		return "user-list";
		
	}
}
