package com.shoesstore.service;

import java.util.List;

import com.shoesstore.model.CartItem;
import com.shoesstore.model.User;

public interface CartItemService {
	public List<CartItem> getCartItemsByUser(User user) ;
	public CartItem addCartItem(CartItem cartItem);
	public void removeCartItem(int cartItemId);
	public void clearCart(User user) ;
}
