package com.shoesstore.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import com.shoesstore.model.Product;
import com.shoesstore.model.ProductSize;

public class ProductSpecifications {

    private final EntityManager entityManager;

    public ProductSpecifications(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static Specification<Product> findAllByCriteria(String status, String name, Integer categoryId,
                                                    Integer brandId, Double minPrice, Double maxPrice, String size) {
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

            if (size != null && !size.isEmpty()) {
                Join<Product, ProductSize> sizesJoin = root.join("sizes", JoinType.INNER);

                Predicate sizePredicate = criteriaBuilder.equal(sizesJoin.get("size"), size);
                Predicate quantityPredicate = criteriaBuilder.gt(sizesJoin.get("quantity"), 0);

                predicates.add(sizePredicate);
                predicates.add(quantityPredicate);
            }

            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
