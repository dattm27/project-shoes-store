package com.shoesstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoesstore.service.UserService;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String showHomePage(Model model) {
		return "/shopper/index";
	}
	
	@GetMapping("user-list")
	public String showUsers(Model model) {
		//hien thi danh sach cac user trong he thong
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
}
