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
		 cartItemRepository.deleteById( cartItemId);

	}

	@Override
	public void clearCart(User user) {
		 List<CartItem> cartItems = cartItemRepository.findByUser(user);
	        cartItemRepository.deleteAll(cartItems);

	}
	

}
