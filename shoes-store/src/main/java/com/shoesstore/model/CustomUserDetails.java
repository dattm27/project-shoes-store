package com.shoesstore.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shoesstore.model.User;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	
	private User user;
	
	public CustomUserDetails(User user) {
		super();
		this.user = user;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		//kiểm tra trạng thái = 1 thì có nghĩa là đã kích hoạt
		return (user.getStatus()==1);
	}


	public User getUser() {
		return user;
	}
	public int getId() {
		// TODO Auto-generated method stub
		return user.getId();
	}
	public String getRole() {
		// TODO Auto-generated method stub
		return user.getRole();
	}
	public String getFullName() {
		return user.getFullName();
	}
}
