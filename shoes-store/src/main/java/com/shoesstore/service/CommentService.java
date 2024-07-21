package com.shoesstore.service;

import java.util.List;

import com.shoesstore.model.Comment;
import com.shoesstore.model.Product;
import com.shoesstore.model.User;

public interface CommentService {

	public Comment addComment (User user, Product product , int rate, String name, String comment) ;
	public List<Comment> getComment(int productId);
}
