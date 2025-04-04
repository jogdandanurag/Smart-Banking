package com.aj.vo;

import java.math.BigDecimal;

public class LoanEnquiryVo {

	
    private long id;

//  private Customer customer; // Assuming you have a Customer entity

  private String loanType; // e.g., HOME_LOAN, CAR_LOAN, PERSONAL_LOAN

  private BigDecimal loanAmount; // Amount the customer is enquiring about

  private int tenure; // Loan tenure in months or years

  private BigDecimal annualIncome; // Annual income of the customer

  private String preferredBankBranch; // Preferred branch for loan processing

  private String loanPurpose; // Purpose of loan, e.g., house purchase, car purchase


  private String contactNumber; // Customer's contact number

  private String email; // Customer's email address

  private String Status; // Status of the enquiry (e.g., PENDING, APPROVED, REJECTED)

  private String createdAt;

  private String updatedAt;
  
  private String createdBy;

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getLoanType() {
	return loanType;
}

public void setLoanType(String loanType) {
	this.loanType = loanType;
}

public BigDecimal getLoanAmount() {
	return loanAmount;
}

public void setLoanAmount(BigDecimal loanAmount) {
	this.loanAmount = loanAmount;
}

public int getTenure() {
	return tenure;
}

public void setTenure(int tenure) {
	this.tenure = tenure;
}

public BigDecimal getAnnualIncome() {
	return annualIncome;
}

public void setAnnualIncome(BigDecimal annualIncome) {
	this.annualIncome = annualIncome;
}

public String getPreferredBankBranch() {
	return preferredBankBranch;
}

public void setPreferredBankBranch(String preferredBankBranch) {
	this.preferredBankBranch = preferredBankBranch;
}

public String getLoanPurpose() {
	return loanPurpose;
}

public void setLoanPurpose(String loanPurpose) {
	this.loanPurpose = loanPurpose;
}

public String getContactNumber() {
	return contactNumber;
}

public void setContactNumber(String contactNumber) {
	this.contactNumber = contactNumber;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getStatus() {
	return Status;
}

public void setStatus(String status) {
	Status = status;
}

public String getCreatedAt() {
	return createdAt;
}

public void setCreatedAt(String createdAt) {
	this.createdAt = createdAt;
}

public String getUpdatedAt() {
	return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
	this.updatedAt = updatedAt;
}

public String getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
  
  
  
}
