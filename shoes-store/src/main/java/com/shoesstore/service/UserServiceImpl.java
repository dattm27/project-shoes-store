package com.shoesstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoesstore.model.User;
import com.shoesstore.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll() ;
	}

}
