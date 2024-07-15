package com.shoesstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shoesstore.model.CustomUserDetails;
import com.shoesstore.service.FavoriteService;

@Controller
public class FavoriteController {
	@Autowired
	private FavoriteService favoriteService;
	
	// xử lý khi người dùng ấn like sản phẩm
	@PostMapping("product-wishlist/{id}")
	public ResponseEntity<?> loveProduct (@PathVariable("id") int productId){

		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (favoriteService.loveProduct(currentUser.getId(), productId) )
		return ResponseEntity.ok("react successfully");
		return ResponseEntity.badRequest().body("react failed");
	}
}
