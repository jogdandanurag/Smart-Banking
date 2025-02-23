package com.aj.vo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserVo {

	private Long id;
	
	private String name;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private byte[] profilePath;	
	
	private String role="ROLE_USER";
	
	private String mobileNumber;
	
	@JsonIgnore
	private long whatsAppNumber;
	
	@JsonIgnore
	private String UpdatedDate;

	private List<Long> teamId = new ArrayList<>();

	private String createdDate; 
	
	private boolean isEnabled = true;

	private boolean isAccountNonExpired = true;
	
	private boolean isAccountNonLocked = true;

	private boolean isCredentialsNonExpired = true;
	
	 private long orginizationId;

	 
	public String getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		UpdatedDate = updatedDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setProfilePath(byte[] profilePath) {
		this.profilePath = profilePath;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public byte[] getProfilePath() {
		return profilePath;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public long getWhatsAppNumber() {
		return whatsAppNumber;
	}

	public void setWhatsAppNumber(long whatsAppNumber) {
		this.whatsAppNumber = whatsAppNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 
public List<Long> getTeamId() {
	return teamId;
}

public void setTeamId(List<Long> teamId) {
	this.teamId = teamId;
}

public boolean isEnabled() {
	return isEnabled;
}

public void setEnabled(boolean isEnabled) {
	this.isEnabled = isEnabled;
}

public boolean isAccountNonExpired() {
	return isAccountNonExpired;
}

public void setAccountNonExpired(boolean isAccountNonExpired) {
	this.isAccountNonExpired = isAccountNonExpired;
}

public long getOrginizationId() {
	return orginizationId;
}

public void setOrginizationId(long orginizationId) {
	this.orginizationId = orginizationId;
}

public boolean isAccountNonLocked() {
	return isAccountNonLocked;
}

public void setAccountNonLocked(boolean isAccountNonLocked) {
	this.isAccountNonLocked = isAccountNonLocked;
}

public boolean isCredentialsNonExpired() {
	return isCredentialsNonExpired;
}

public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
	this.isCredentialsNonExpired = isCredentialsNonExpired;
}	
	
}
