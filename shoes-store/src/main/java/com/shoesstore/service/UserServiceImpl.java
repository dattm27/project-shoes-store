package com.shoesstore.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

import com.shoesstore.model.User;
import com.shoesstore.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;

@Service
@EnableWebSecurity
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {

		return userRepository.findAll();
	}
	
	private final JavaMailSender mailSender;

	UserServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public User registerUser(String fullName, String email, String encodedPassword, String phoneNumber) {
		// Kiểm tra xem email đã tồn tại chưa
		if (userRepository.findByEmail(email) != null) {
			return null; // Email đã tồn tại
		} else {
			// Tạo người dùng mới và lưu vào cơ sở dữ liệu
			User user = new User();
			user.setFullName(fullName);
			user.setEmail(email);
			user.setPassword(encodedPassword);
			user.setPhoneNumber(phoneNumber);
			user.setStatus(0); // chưa kích hoạt

			String randomCode = RandomString.make(64);
			user.setVerificationCode(randomCode);

			return userRepository.save(user);

		}

	}

	// gửi mã xác thực vào email cho người dùng khi đăng ký tài khoản mới
	@Override
	public void sendVerificationCode(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
		String subject = "Vui lòng xác nhận đăng ký tài khoản";
		String sendername = "Shoes Store";
		String mailContent = "<p>Xin chào, " + user.getFullName() + ", </p>";
		mailContent += "<p>Vui lòng click vào link dưới đây để xác nhận đăng ký tài khoản tại Work CV: </p>";
		String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
		mailContent += "<h3><a href=\"" + verifyURL + "\">Xác nhận </a></h3>";
		mailContent += "<p>Xin chân thành cảm ơn, Work CV Team </p>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("dattran2003.ttn@gmail.com", sendername);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);

		mailSender.send(message);

	}
	//khi người dùng bấm vào link xác thực, đổi trạng thái từ 0 sang 1 -> enable tài khoản mới
	
	@Override
	public boolean verify(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);
		//không thấy tài khoản hoặc đã được kích hoạt rồi
		if(user == null || user.getStatus()==1) {
			return false;
		}
		else {
			user.setStatus(1);
			userRepository.save(user);
			return true;
		}
	}
	//Cập nhật thông tin cá nhân
	@Override
	public User update(int id, String fullName, String phone, String address) {
		User user = userRepository.findById(id).get();
		user.setFullName(fullName);
		user.setAddress(address);
		user.setPhoneNumber(phone);
		
	
		return  userRepository.save(user);
	}

}
