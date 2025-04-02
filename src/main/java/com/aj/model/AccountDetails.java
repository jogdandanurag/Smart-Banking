package com.aj.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_banking_account")
public class AccountDetails {

	      @Id
	     @GeneratedValue(strategy = GenerationType.AUTO)
         private long id;
	    
	     private String firstName;
	    
	     private String lastName;
	    
	     private String email;
	     
	     private AccountStatus accountStatus;
	    
	     private String phoneNumber;
	    
	     private AccountType accountType; // e.g., Savings, Checking
	    
	     private String addressLine1;
	    
	     private String addressLine2;
	    
	     private String city;
	    
	     private String state;
	    
	     private String postalCode;
	     
	     private String createdBy;
	     
	     private double balance;
	    
	     private String country;
	     
	     private Timestamp createdAt;
	     
	     private Timestamp updatedAt;
	     
	     private long bankId;
	     
	     private String panCard;
	     
	     private String adhaarCard;
	     
	     
	
	     @PrePersist
	 	protected void onCreate() {
	 		createdAt = new Timestamp(System.currentTimeMillis());
	 	}

	 	@PreUpdate
	 	protected void onUpdate() {
	 		updatedAt = new Timestamp(System.currentTimeMillis());
	 	}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public AccountStatus getAccountStatus() {
			return accountStatus;
		}

		public void setAccountStatus(AccountStatus accountStatus) {
			this.accountStatus = accountStatus;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getAddressLine1() {
			return addressLine1;
		}

		public void setAddressLine1(String addressLine1) {
			this.addressLine1 = addressLine1;
		}

		public String getAddressLine2() {
			return addressLine2;
		}

		public void setAddressLine2(String addressLine2) {
			this.addressLine2 = addressLine2;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public double getBalance() {
			return balance;
		}

		public void setBalance(double balance) {
			this.balance = balance;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}

		public String getCountry() {
			return country;
		}

		public String getPanCard() {
			return panCard;
		}

		public void setPanCard(String panCard) {
			this.panCard = panCard;
		}

		public String getAdhaarCard() {
			return adhaarCard;
		}

		public void setAdhaarCard(String adhaarCard) {
			this.adhaarCard = adhaarCard;
		}

		public long getBankId() {
			return bankId;
		}

		public void setBankId(long bankId) {
			this.bankId = bankId;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public Timestamp getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(Timestamp createdAt) {
			this.createdAt = createdAt;
		}

		public Timestamp getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(Timestamp updatedAt) {
			this.updatedAt = updatedAt;
		}

		public AccountType getAccountType() {
			return accountType;
		}

		public void setAccountType(AccountType accountType) {
			this.accountType = accountType;
		}
	 	
	 	
}
