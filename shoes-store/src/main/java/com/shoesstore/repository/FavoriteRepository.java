package com.shoesstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoesstore.model.Favorite;
import com.shoesstore.model.Product;
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	Favorite findByUserIdAndProductId(int userId, int productId);

	List<Favorite> findAllByUserId(int userId);
}
