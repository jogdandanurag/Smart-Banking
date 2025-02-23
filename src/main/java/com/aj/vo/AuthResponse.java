package com.aj.vo;

public class AuthResponse {

	private String token;
	private String role;
	private byte[] profile;
	private String email;
	private String username;
	


	public AuthResponse(String token, String role, byte[] profile,String email, String username) {
		super();
		this.token = token;
		this.role = role;
		this.profile=profile;
		this.email=email;
		this.username=username;
	}

	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public AuthResponse() {
	}

	public byte[] getProfile() {
		return profile;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}