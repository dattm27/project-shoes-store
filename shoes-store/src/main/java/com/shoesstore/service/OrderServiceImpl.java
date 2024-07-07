package com.shoesstore.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shoesstore.model.CartItem;
import com.shoesstore.model.Order;
import com.shoesstore.model.OrderItem;
import com.shoesstore.model.User;
import com.shoesstore.repository.OrderRepository;
import com.shoesstore.service.OrderSpecifications;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartItemService cartItemService;
	//lưu order mới
	@Override
	public Order createOrder(User user, List<CartItem> cartItems,
			String recipient, String deliveryAddress, String phoneNumber, String paymentMethod ) {
		// Tạo đơn hàng mới
		Order order = new Order();
		order.setUser(user);
		order.setCreatedDate(LocalDateTime.now());
		order.setOrderItems(new ArrayList<>());
		order.setPaymentStatus("Chưa thanh toán");
		order.setShippingStatus("Chưa xét duyệt");
		order.setTotalAmount(cartItemService.calculateTotalAmount(user));
		order.setPaymentMethod(paymentMethod);
		order.setDeliveryAddress(deliveryAddress);
		order.setRecipient(recipient);
		order.setPhoneNumber(phoneNumber);
		// Thêm các mục trong giỏ hàng vào đơn hàng
		for (CartItem cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProductSize().getProduct());
			orderItem.setSize(cartItem.getProductSize().getSize());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(cartItem.getProductSize().getProduct().getPrice());

			order.getOrderItems().add(orderItem);
		}

		// Lưu đơn hàng vào cơ sở dữ liệu
		Order savedOrder = orderRepository.save(order);
		return savedOrder;
	}
	//lay tat ca cac don hang
	@Override
	public List<Order> getAllOrders() {
		
		return orderRepository.findAll();
	}
	//loc cac don hang theo cac tieu chi
	@Override
	public List<Order> getFilteredOrders(String name, String method, String paymenStatus, String shippingStatus) {
		Specification<Order> specs = Specification.where(OrderSpecifications.findAllByCriteria(name, method, paymenStatus, shippingStatus));
		return orderRepository.findAll(specs);
	}
	
	//tra ve don hang co id id
	@Override
	public Order getOrderById(int id) {
		
		return orderRepository.findById(id).get();
	}
	@Override
	public Order updateStatus(int id, String status) {
		Order order = orderRepository.findById(id).get();
		if (order!= null ) {
			order.setShippingStatus(status);
		}
		Order saved = orderRepository.save(order);
		return saved;
	}
	@Override
	public Order cancelOrder(int id) {
		Order order = orderRepository.findById(id).get();
		if (order!= null ) {
			order.setShippingStatus("Đã hủy");
		}
		Order saved = orderRepository.save(order);
		return saved;
	}
	@Override
	public List<Order> getOrdersOfUserId(int user_id) {
		List<Order>  listOrders= orderRepository.findAllByUserIdOrderByIdDesc(user_id);
		return listOrders;
	}

}
