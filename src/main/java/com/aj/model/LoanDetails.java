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
@Table(name = "tbl_banking_loan")
public class LoanDetails {

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long accountId; // Reference to the borrower's account

    private String borrowerName;

    private BigDecimal loanAmount; // Total loan amount

    private BigDecimal interestRate; // Annual interest rate in percentage

    private int tenureInMonths; // Loan tenure in months

    private LoanType loanType; // HOME_LOAN, CAR_LOAN, PERSONAL_LOAN, etc.

    private LoanStatus loanStatus; // ACTIVE, CLOSED, DEFAULTED, etc.

    private BigDecimal emiAmount; // Monthly EMI amount

    private Timestamp loanStartDate;

    private Timestamp loanEndDate;

    private BigDecimal remainingBalance; // Remaining amount to be paid

    private BigDecimal totalInterestPaid; // Cumulative interest paid

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String createdBy;

    private String updatedBy;
    
    private BigDecimal disburstAmount;
    
    private Timestamp disbrstDate;


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

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public int getTenureInMonths() {
		return tenureInMonths;
	}

	public void setTenureInMonths(int tenureInMonths) {
		this.tenureInMonths = tenureInMonths;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public LoanStatus getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}

	public BigDecimal getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(BigDecimal emiAmount) {
		this.emiAmount = emiAmount;
	}

	public Timestamp getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Timestamp loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public Timestamp getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Timestamp loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public BigDecimal getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(BigDecimal remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public BigDecimal getTotalInterestPaid() {
		return totalInterestPaid;
	}

	public void setTotalInterestPaid(BigDecimal totalInterestPaid) {
		this.totalInterestPaid = totalInterestPaid;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public BigDecimal getDisburstAmount() {
		return disburstAmount;
	}

	public void setDisburstAmount(BigDecimal disburstAmount) {
		this.disburstAmount = disburstAmount;
	}

	public Timestamp getDisbrstDate() {
		return disbrstDate;
	}

	public void setDisbrstDate(Timestamp disbrstDate) {
		this.disbrstDate = disbrstDate;
	}
}
