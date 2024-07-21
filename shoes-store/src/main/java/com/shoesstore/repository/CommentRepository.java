package com.shoesstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoesstore.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findAllByProductId(int productId);

}
