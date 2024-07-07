package com.shoesstore.service;

import com.shoesstore.model.Order;
import com.shoesstore.model.User;

import java.util.List;

import com.shoesstore.model.CartItem;

public interface OrderService {
	public Order createOrder(User user, List<CartItem> cartItems, String recipient, String deliveryAddress, String phoneNumber, String paymentMethod );
	
	//Lấy ra danh sách order mà một user đặt - for admin
	public List<Order> getAllOrders();
	public List<Order> getFilteredOrders(String name, String method, String paymenStatus, String shippingStatus);
	public Order getOrderById(int id);
	//Cập nhât trạng thái đơn hàng
	public Order updateStatus(int id, String status);
	//Huỷ một đơn 
	public Order cancelOrder(int id);
	
	//Lấy ra danh sách order mà một user đặt - for user
	public List<Order> getOrdersOfUserId(int user_id);
}
