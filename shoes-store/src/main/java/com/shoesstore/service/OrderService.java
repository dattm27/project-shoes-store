package com.shoesstore.service;

import com.shoesstore.model.Order;
import com.shoesstore.model.User;

import java.util.List;

import com.shoesstore.model.CartItem;

public interface OrderService {
	public Order createOrder(User user, List<CartItem> cartItems, String recipient, String deliveryAddress, String phoneNumber, String paymentMethod );
	
	public List<Order> getAllOrders();
	public List<Order> getFilteredOrders(String name, String method, String paymenStatus, String shippingStatus);
}
