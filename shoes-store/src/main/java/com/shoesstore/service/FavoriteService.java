package com.shoesstore.service;

import java.util.List;

import com.shoesstore.model.Favorite;
import com.shoesstore.model.Product;

public interface FavoriteService {
	boolean loveProduct(int userId, int productId) ;

	boolean isLoved(int id, int productId);

	List<Product> getFavoritedList(int id);
	
}
