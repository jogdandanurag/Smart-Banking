package com.aj.vo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aj.model.User;

public class UserInfoDetails implements UserDetails {
	private static final long serialVersionUID = 2764889613442161525L;
	private String username;
	private String password;
	private Long orginizationId;
	private List<GrantedAuthority> authorities;
	private boolean isEnabled;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;

	public UserInfoDetails(User userInfo) {
		username = userInfo.getUsername();
		password = userInfo.getPassword();
		authorities = Arrays.stream(userInfo.getRole().name().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		orginizationId=userInfo.getOrginizationId();
		isEnabled=userInfo.isEnabled();
		isAccountNonLocked=userInfo.isAccountNonLocked();
		isAccountNonExpired=userInfo.isAccountNonExpired();
		isCredentialsNonExpired=userInfo.isCredentialsNonExpired();
	
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	public Long getOrginizationId() {
		return orginizationId;
	}

	
}