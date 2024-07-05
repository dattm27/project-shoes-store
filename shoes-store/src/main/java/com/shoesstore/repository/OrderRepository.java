package com.shoesstore.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoesstore.model.Order;
import com.shoesstore.model.Product;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	//loc don hang theo cac tieu chi
	List<Order> findAll(Specification<Order> spec);
}
