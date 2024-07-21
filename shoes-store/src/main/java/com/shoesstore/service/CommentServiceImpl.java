package com.shoesstore.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoesstore.model.Comment;
import com.shoesstore.model.Product;
import com.shoesstore.model.User;
import com.shoesstore.repository.CommentRepository;

@Service

public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Override
	public Comment addComment(User user, Product product , int rate, String name, String _comment) {
		Comment comment = new Comment (user, product, rate , name , _comment);

		return 	 commentRepository.save(comment);
	}
	@Override
	public List<Comment> getComment(int productId) {
		List<Comment> comments  = commentRepository.findAllByProductId(productId);
		return comments;
	}

}
