package com.shoesstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoesstore.model.Favorite;
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	Favorite findByUserIdAndProductId(int userId, int productId);
}
