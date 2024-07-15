package com.shoesstore.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
@Table(name = "favorite", uniqueConstraints = { 
	    @UniqueConstraint(columnNames = { "user_id", "product_id" }) 
})
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	@ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	private LocalDateTime createdDate;
	
	
	
	public Favorite() {
		super();
	}

	public Favorite(User user, Product product) {
		super();
		this.user = user;
		this.product = product;
		  
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
	 
	 
	 
}
