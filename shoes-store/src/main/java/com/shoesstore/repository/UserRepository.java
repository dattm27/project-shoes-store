package com.shoesstore.repository;

import com.shoesstore.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{
	 User findByEmail(String email);
	 User findByVerificationCode(String verifycationCode);
}
