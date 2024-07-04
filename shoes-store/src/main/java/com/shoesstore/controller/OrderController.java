package com.shoesstore.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoesstore.model.CartItem;
import com.shoesstore.model.Order;
import com.shoesstore.model.OrderItem;
import com.shoesstore.model.User;
import com.shoesstore.service.CartItemService;
import com.shoesstore.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private OrderService orderService;
	@PostMapping("/checkout")
	public String checkoutOrder(Model model, @RequestParam("paymentMethod") String paymentMethod, 
			@RequestParam("name") String recipient, @RequestParam("address") String deliveryAddress, @RequestParam("phone") String phoneNumber) {
		com.shoesstore.model.CustomUserDetails currentUser = (com.shoesstore.model.CustomUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = currentUser.getUser();
		List<CartItem> cartItems = cartItemService.getCartItemsByUser(user);
		Order order = orderService.createOrder(user, cartItems,recipient, deliveryAddress, phoneNumber, paymentMethod);
		

		// Xóa tất cả các mục trong giỏ hàng của người dùng
		cartItemService.clearCart(user);
		model.addAttribute("result", "Đặt hàng thành công");
		model.addAttribute("note", "Cảm ơn bạn đã mua hàng tại TopShoe");
		
		model.addAttribute("isSuccess", true);
		model.addAttribute("order_id", order.getId());
		return "shopper/order-result";
	}
}
