package com.shoesstore.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name="Order_")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference // Chỉ định đây là phía "quản lý" của quan hệ - tránh lỗi vòng lặp JSON
    private List<OrderItem> orderItems;
    
    @Column(name = "recipient", nullable = false)
    private String recipient;
    
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress; 
    
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; 
   

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;
    
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "shipping_status", nullable = false)
    private String shippingStatus;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
    
    
    // Constructors, getters, setters
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	 public static String formatedPrice(double price) {
	        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
	        symbols.setGroupingSeparator(',');
	        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
	        return formatter.format(price) + "đ";
	    }
	

    
}