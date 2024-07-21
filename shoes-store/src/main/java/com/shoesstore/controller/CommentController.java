package com.shoesstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoesstore.model.CustomUserDetails;
import com.shoesstore.model.Product;
import com.shoesstore.service.CommentService;
import com.shoesstore.service.ProductService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private ProductService productService;
	@PostMapping("/comment")
	public ResponseEntity<?> loveProduct (@RequestParam(name ="name") String name , @RequestParam(name ="comment") String comment, @RequestParam(name="productId") int productId , @RequestParam(name = "rating") int rate){

		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Product product = productService.getProduct(productId);
		if (commentService.addComment(currentUser.getUser(), product, rate, name, comment) != null)
		return ResponseEntity.ok("react successfully");
		return ResponseEntity.badRequest().body("react failed");
	}
	

}
