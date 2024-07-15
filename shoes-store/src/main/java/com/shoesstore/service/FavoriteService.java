package com.shoesstore.service;

import com.shoesstore.model.Favorite;

public interface FavoriteService {
	boolean loveProduct(int userId, int productId) ;

	boolean isLoved(int id, int productId);
	
}
