package com.shoesstore.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.shoesstore.model.User;

import jakarta.mail.MessagingException;

public interface UserService {
	List<User> getAllUsers();

	User registerUser(String fullName, String email, String encodedPassword, String phoneNumber);
	//gửi email xác thực tài khoản người dùng
	public void sendVerificationCode(User user, String siteURL) throws UnsupportedEncodingException, MessagingException ;
	//khi người dùng bấm vào link xác thực
	public boolean verify(String code);

	User update(int id, String fullName, String phone, String address);
	User findUserByEmail(String email);
}
