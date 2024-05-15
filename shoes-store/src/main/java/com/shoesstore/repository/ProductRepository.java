package com.shoesstore.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoesstore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findAllByStatus(String status);
	List<Product> findAll(Specification<Product> spec);
	List<Product> findAllByDeleted(boolean delete);
}
