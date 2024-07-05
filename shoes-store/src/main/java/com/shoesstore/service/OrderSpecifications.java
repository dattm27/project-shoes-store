package com.shoesstore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.shoesstore.model.Order;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class OrderSpecifications {
	public static Specification<Order> findAllByCriteria(String name, String method, String paymentStatus,
			String shippingStatus) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (name != null && !name.isEmpty()) {
				
				
				// Cố gắng chuyển đổi chuỗi name sang kiểu double
	            try {
	                double nameAsDouble = Double.parseDouble(name);
	                // Nếu thành công, thêm điều kiện tìm kiếm trong trường totalAmount
	                predicates.add(criteriaBuilder.equal(root.get("totalAmount"), nameAsDouble));
	            } catch (NumberFormatException e) {
	                // Nếu không thành công, không thêm điều kiện cho totalAmount
	                // Bạn có thể ghi log hoặc xử lý ngoại lệ ở đây nếu cần
	            	predicates.add(criteriaBuilder.like(root.get("recipient"), "%" + name + "%"));
	            }

			}
			


		    
			if (method != null && !method.isEmpty()) {
			    predicates.add(criteriaBuilder.equal(
			        criteriaBuilder.lower(root.get("paymentMethod")),
			        method.toLowerCase()
			    ));
			}
			if (paymentStatus != null && !paymentStatus.isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus));
			}
			if (shippingStatus != null && !shippingStatus.isEmpty()) {
				predicates.add(criteriaBuilder.like(root.get("shippingStatus"), "%" + shippingStatus ));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};

	}

}
