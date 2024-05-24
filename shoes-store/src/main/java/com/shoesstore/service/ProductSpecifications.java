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

	public static Specification<Product> findAllByCriteria(String status, String name, Integer categoryId,
			Integer brandId, Double minPrice, Double maxPrice) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (status != null && !status.isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("status"), status));
			}

			if (name != null && !name.isEmpty()) {
				predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
			}

			if (categoryId != null) {
				predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
			}

			if (brandId != null) {
				predicates.add(criteriaBuilder.equal(root.get("brand").get("id"), brandId));
			}

			if (minPrice != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
			}

			if (maxPrice != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
			}

			// Thêm điều kiện sản phẩm chưa bị xoá
			predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}