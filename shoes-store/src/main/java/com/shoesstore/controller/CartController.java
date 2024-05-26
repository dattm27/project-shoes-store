package com.shoesstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoesstore.model.CartItem;
import com.shoesstore.service.CartItemService;
import com.shoesstore.service.ProductService;
import com.shoesstore.service.ProductSizeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CartController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductSizeService sizeService;
	
	@Autowired
	private CartItemService cartItemService;
	
	
	
	@PostMapping("/add-to-cart")
	@ResponseBody
	public ResponseEntity<Object> addToCart(HttpServletRequest request) {
		//lấy thông tin khách
		// Lấy ra người dùng hiện tại
				com.shoesstore.model.CustomUserDetails currentUser = (com.shoesstore.model.CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		int customer_id = currentUser.getId();
		
		//lấy thông tin sản phẩm 
		int product_id = Integer.parseInt(request.getParameter("product_id"));
		int size_id = Integer.parseInt(request.getParameter("size_id"));
		
		//tạo ra cart item mới
		CartItem cartItem = new CartItem(currentUser.getUser(), productService.findById(product_id), sizeService.getSizeById(size_id), 1);
		//System.out.println("add to cart " + customer_id + " " + product_id+ " " + size_id);
		if (cartItemService.addCartItem(cartItem)!= null) 	return ResponseEntity.ok().body(null);
		else return  ResponseEntity.internalServerError().body(null);
		
	
	}
	
	//cho biết có bao  nhiêu sản phẩm trong giỏ hàng
	@PostMapping("/cart-header")
	@ResponseBody
	public ResponseEntity<Object> getCartHeader() {
		// Lấy ra người dùng hiện tại
		com.shoesstore.model.CustomUserDetails currentUser = (com.shoesstore.model.CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int size = cartItemService.getCartItemsByUser(currentUser.getUser()).size();
		System.out.print(size);
		return ResponseEntity.ok().body(size);
	}
	
	
}
