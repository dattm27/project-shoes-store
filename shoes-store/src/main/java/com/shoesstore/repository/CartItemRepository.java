package com.shoesstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoesstore.model.CartItem;
import com.shoesstore.model.User;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	 List<CartItem> findByUser(User user);
}
