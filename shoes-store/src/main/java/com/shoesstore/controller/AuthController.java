package com.shoesstore.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shoesstore.model.User;
import com.shoesstore.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/signup")
	public String signUpForm() {

		return "shopper/register";
	}

	@PostMapping("/save-user")
	public String register(@RequestParam("email") String email, @RequestParam("full-name") String fullName,
			@RequestParam("password") String password, @RequestParam("phone_number") String phoneNumber, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException, MessagingException {
		// Mã hóa mật khẩu
		String encodedPassword = passwordEncoder.encode(password);
		// gọi service đăng ký tài khoản
		User user = userService.registerUser(fullName, email, encodedPassword, phoneNumber);
		// nếu user là null thì không đăng ký được tài khoản (do đã tồn tai email đó)
		if (user == null) {
			// nếu đã tồn tại email như thế
			model.addAttribute("error", true);
			return "shopper/register";
		} else {
			// tạo link để gửi người dùng bấm vào xác thực tài khoản (link này sau đó sẽ
			// được thêm mã xác thực vào)
			String siteURL = request.getRequestURL().toString();
			userService.sendVerificationCode(user, siteURL);
			// dang ky thanh cong
			// model.addAttribute("sign_up_success", true);
			model.addAttribute("msg", "Đăng ký thành công! Kiểm tra email để xác thực tài khoản");
			return "/login";
		}
	}

	// xác thực người dùng
	@GetMapping("/save-user/verify")
	public String verifyAccount(@RequestParam("code") String code, Model model) {

		boolean verify = userService.verify(code);
		if (verify) {
			String msg = "Xác thực thành công! Vui lòng đăng nhập!";
			model.addAttribute("msg", msg);
		} else {
			String error = "Gặp lỗi khi xác thực";
			model.addAttribute("error", error);
		}
		 return "redirect:/";
	}
	
	 @GetMapping("/signout")
	    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
	        if (authentication != null) {
	            new SecurityContextLogoutHandler().logout(request, response, authentication);
	        }
	        return "redirect:/";
	    }
}
