package com.shoesstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shoesstore.model.ProductSize;
@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize,Integer > {
	 @Query("SELECT DISTINCT p.size FROM ProductSize p")
	    List<String> findDistinctSizes();
}
