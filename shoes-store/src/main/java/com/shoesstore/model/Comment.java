package com.shoesstore.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String comment;
	
	@Column
	private int rate;
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	@ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	private LocalDateTime createdDate;

	

	public Comment() {
		super();
	}

	public Comment( User user, Product product ,   int rate, String name, String comment) {
		super();
		this.name = name;
		this.comment = comment;
		this.rate = rate;
		this.user = user;
		this.product = product;
		this.createdDate = LocalDateTime.now(); // Đặt thời gian tạo bằng giờ hiện tại
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	  // Phương thức trả về ngày tháng định dạng
    public String getFormattedCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return "on " + createdDate.format(formatter);
    }
	
	
}
