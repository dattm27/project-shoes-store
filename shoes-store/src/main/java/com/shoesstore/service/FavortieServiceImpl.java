package com.shoesstore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoesstore.model.Favorite;
import com.shoesstore.model.Product;
import com.shoesstore.model.User;
import com.shoesstore.repository.FavoriteRepository;
import com.shoesstore.repository.ProductRepository;
import com.shoesstore.repository.UserRepository;
@Service
public class FavortieServiceImpl implements FavoriteService {
	@Autowired
	private FavoriteRepository favoriteRepository;
	@Autowired
	private UserRepository userRepository; 
	@Autowired
	private  ProductRepository productRepository;
 
	@Override
	public boolean loveProduct(int userId, int productId) {
		User user = userRepository.findById(userId).get();
		Product product = productRepository.findById(productId).get();
		//nếu chưa thích sản phẩm này
		Favorite fav = favoriteRepository.findByUserIdAndProductId(userId, productId);
		if (fav == null) {
			Favorite favorite = new Favorite(user, product);
			if ( 	favoriteRepository.save(favorite) != null ) return true;
			
		}
		else {
			favoriteRepository.delete(fav);
			return true;
		}
		return false;
		
		
	}

	@Override
	public boolean isLoved(int userId, int productId) {
		Favorite fav = favoriteRepository.findByUserIdAndProductId(userId, productId);
		return (fav != null);
	}

	@Override
	public List<Product> getFavoritedList(int userId) {
		List<Favorite> fav = favoriteRepository.findAllByUserId(userId);
		return  fav.stream()
                .map(Favorite::getProduct)
                .collect(Collectors.toList());
	}

}
