package com.shoesstore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoesstore.model.CartItem;
import com.shoesstore.model.User;
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
		// lấy thông tin khách
		// Lấy ra người dùng hiện tại
		com.shoesstore.model.CustomUserDetails currentUser = (com.shoesstore.model.CustomUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		int customer_id = currentUser.getId();

		// lấy thông tin sản phẩm
		int product_id = Integer.parseInt(request.getParameter("product_id"));
		int size_id = Integer.parseInt(request.getParameter("size_id"));

		// tạo ra cart item mới
		CartItem cartItem = new CartItem(currentUser.getUser(), productService.findById(product_id),
				sizeService.getSizeById(size_id), 1);
		// System.out.println("add to cart " + customer_id + " " + product_id+ " " +
		// size_id);
		if (cartItemService.addCartItem(cartItem) != null)
			return ResponseEntity.ok().body(null);
		else
			return ResponseEntity.internalServerError().body(null);

	}

	// cho biết có bao nhiêu sản phẩm trong giỏ hàng
	@PostMapping("/cart-header")
	@ResponseBody
	public ResponseEntity<Object> getCartHeader() {
		// Lấy ra người dùng hiện tại
		com.shoesstore.model.CustomUserDetails currentUser = (com.shoesstore.model.CustomUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		int size = cartItemService.getCartItemsByUser(currentUser.getUser()).size();
		System.out.print(size);
		return ResponseEntity.ok().body(size);
	}

	// xem giỏ hàng
	@GetMapping("/cart")
	public String seeCart(Model model) {
		// Lấy ra người dùng hiện tại
		com.shoesstore.model.CustomUserDetails currentUser = (com.shoesstore.model.CustomUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = currentUser.getUser();
		List<CartItem> cartItems= cartItemService.getCartItemsByUser(user);
		model.addAttribute("customer", user);
		model.addAttribute("totalAmount", cartItemService.calculateTotalAmount(user));
		if (cartItems.size() > 0) {
			model.addAttribute("cartItems",cartItems);
			
			return "shopper/cart";
		}
			
		else
			return "shopper/empty-cart";
	}
	
	 	@PostMapping("/cart/change-quantity")
	    public ResponseEntity<?> changeQuantity(@RequestParam("id") int cartItemId,
	                                            @RequestParam("change") String change) {
	 		com.shoesstore.model.CustomUserDetails currentUser = (com.shoesstore.model.CustomUserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = currentUser.getUser();
	        try {
	        	 int newQuantity = cartItemService.changeQuantity(cartItemId, change);
	             double subtotal = cartItemService.calculateSubtotal(cartItemId);
	             double totalAmount = cartItemService.calculateTotalAmount(user);
	             return ResponseEntity.ok(Map.of("newQuantity", newQuantity, "subtotal", subtotal, "totalAmount", totalAmount));
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Failed to update quantity");
	        }
	    }
	 	//Xoá một sản phẩm khỏi cart
	 	@PostMapping("/cart/remove-cart")
	 	@ResponseBody
	 	public Map<String, Object> removeCartItem(@RequestParam(name="id") int id) {
	 		com.shoesstore.model.CustomUserDetails currentUser = (com.shoesstore.model.CustomUserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = currentUser.getUser();
	 	    // Xoá cartItem
	 	    cartItemService.removeCartItem(id);

	 	    // Tính toán totalAmount mới
	 	    double totalAmount = cartItemService.calculateTotalAmount(user);

	 	    // Trả về JSON với totalAmount mới
	 	    Map<String, Object> response = new HashMap<>();
	 	    response.put("totalAmount", totalAmount);

	 	    return response;
	 	}
		@GetMapping("/cart/clear")
		public String clearCart() {
			com.shoesstore.model.CustomUserDetails currentUser = (com.shoesstore.model.CustomUserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = currentUser.getUser();
			cartItemService.clearCart(user);
			return "shopper/empty-cart";
		}
		
		
}
