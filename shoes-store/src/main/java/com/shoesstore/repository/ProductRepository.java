package com.shoesstore.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shoesstore.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findAllByStatus(String status);
	List<Product> findAll(Specification<Product> spec);
}
