package com.shoesstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoesstore.model.CartItem;
import com.shoesstore.model.User;
import com.shoesstore.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public List<CartItem> getCartItemsByUser(User user) {
		return cartItemRepository.findByUser(user);
	}

	@Override
	public CartItem addCartItem(CartItem cartItem) {
		return cartItemRepository.save(cartItem);

	}

	@Override
	public void removeCartItem(int cartItemId) {
		cartItemRepository.deleteById(cartItemId);

	}

	@Override
	public void clearCart(User user) {
		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		cartItemRepository.deleteAll(cartItems);

	}
	
	
	//đổi số lượng một sản phẩm trong giỏi
	@Override
	public int changeQuantity(int cartItemId, String change) {
		CartItem cartItem = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid cart item ID"));

		if ("plus".equals(change)) {
			cartItem.setQuantity(cartItem.getQuantity() + 1);
		} else if ("minus".equals(change) && cartItem.getQuantity() > 1) {
			cartItem.setQuantity(cartItem.getQuantity() - 1);
		}

		cartItemRepository.save(cartItem);
		return cartItem.getQuantity();

	}
	//tính subtotal của sản phẩm
	@Override
	public double calculateSubtotal(int cartItemId) {
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found"));
	    return cartItem.getProduct().getPrice() * cartItem.getQuantity();
	
	}
	
	//tính tổng hoá đơn giỏ hàng
	@Override
	public double calculateTotalAmount(User user) {
	    List<CartItem> cartItems = cartItemRepository.findByUser(user);
	    return cartItems.stream()
	                    .mapToDouble(item -> item.getProductSize().getProduct().getPrice() * item.getQuantity())
	                    .sum();
	}
	@Override
	public CartItem getCartItemById(int id) {
		return cartItemRepository.findById(id).get();
	}
}
