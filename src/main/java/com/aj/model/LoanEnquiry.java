package com.aj.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_banking_laonenquiry")
public class LoanEnquiry {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    private Customer customer; // Assuming you have a Customer entity

    private LoanType loanType; // e.g., HOME_LOAN, CAR_LOAN, PERSONAL_LOAN

    private BigDecimal loanAmount; // Amount the customer is enquiring about

    private int tenure; // Loan tenure in months or years

    private BigDecimal annualIncome; // Annual income of the customer

    private String preferredBankBranch; // Preferred branch for loan processing

    private String loanPurpose; // Purpose of loan, e.g., house purchase, car purchase


    private String contactNumber; // Customer's contact number

    private String email; // Customer's email address

    private EnquiryStatus Status; // Status of the enquiry (e.g., PENDING, APPROVED, REJECTED)

    private Timestamp createdAt;

    private Timestamp updatedAt;
    
    private String createdBy;
    
    
    
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

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
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

	public EnquiryStatus getStatus() {
		return Status;
	}

	public void setStatus(EnquiryStatus status) {
		Status = status;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


}
