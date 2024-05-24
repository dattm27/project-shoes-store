package com.shoesstore.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.shoesstore.model.Product;

//cụ thể hoá các yêu cầu tìm kiếm sản phẩm
public class ProductSpecifications {

	public static Specification<Product> findAllByCriteria(String status,
			String name , Integer categoryId, Integer brandId) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (status != null && !status.isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("status"), status));
			}

			if (name != null && !name.isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("name"), name));
			}
			
			

			// Sản phẩm này phải là sản phẩm chưa bị xoá
			// Thêm điều kiện sản phẩm chưa bị xoá
			predicates.add(criteriaBuilder.equal(root.get("is_deleted"), false));
			// Thêm các điều kiện tìm kiếm cho các thuộc tính khác

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	public static Specification<Product> hasStatus(String status) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
	}

	public static Specification<Product> hasName(String name) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
	}

	public static Specification<Product> hasBrandId(Integer brandId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("brand.id"), "%" + brandId + "%");
	}

	public static Specification<Product> hasCategoryId(Integer categoryId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("category.id"), "%" + categoryId + "%");
	}

}
