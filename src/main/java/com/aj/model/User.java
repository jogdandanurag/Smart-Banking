package com.aj.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_banking_users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;

	@Column(name = "user_role")
	private Role role;
	
	@Column(name="mobile_number")
	private String mobileNumber;
	
	@Column(name="whatsapp_number")
	private String whatsAppNumber;
	
	@Column(name="profile_path")
	private String profilePath;
	 
	@Column(name="created_date")
	private Timestamp createdDate;
	
	@Column(name="updated_date")
	private Timestamp UpdatedDate;
	
	@Column(name="is_enabled")
	private boolean isEnabled = true;

	@Column(name="is_account_non_expired")
	private boolean isAccountNonExpired = true;

	@Column(name="is_account_non_locked")
	private boolean isAccountNonLocked = true;

	private long bankId;
	
	
	
	@Column
	private boolean isCredentialsNonExpired = true;
	
	@PrePersist
	protected void onCreate() {
		createdDate = new Timestamp(System.currentTimeMillis());
	}

	@PreUpdate
	protected void onUpdate() {
		UpdatedDate = new Timestamp(System.currentTimeMillis());
	}


	public Timestamp getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		UpdatedDate = updatedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getWhatsAppNumber() {
		return whatsAppNumber;
	}

	public void setWhatsAppNumber(String whatsAppNumber) {
		this.whatsAppNumber = whatsAppNumber;
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

	public Role getRole() {
		return role;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
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

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	
	
	}


	

}