package com.aj.vo;

import java.math.BigDecimal;

public class LoanDetailsVo {

	  private long id;

	    private long accountId; // Reference to the borrower's account

	    private String borrowerName;

	    private BigDecimal loanAmount; // Total loan amount

	    private BigDecimal interestRate; // Annual interest rate in percentage

	    private int tenureInMonths; // Loan tenure in months

	    private String loanType; // HOME_LOAN, CAR_LOAN, PERSONAL_LOAN, etc.

	    private String loanStatus; // ACTIVE, CLOSED, DEFAULTED, etc.

	    private BigDecimal emiAmount; // Monthly EMI amount

	    private String loanStartDate;

	    private String loanEndDate;

	    private BigDecimal remainingBalance; // Remaining amount to be paid

	    private BigDecimal totalInterestPaid; // Cumulative interest paid

	    private String createdAt;

	    private String updatedAt;

	    private String createdBy;

	    private String updatedBy;
	    
	    private BigDecimal disburstAmount;
	    
	    private String disbrstDate;

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

		public String getLoanType() {
			return loanType;
		}

		public void setLoanType(String loanType) {
			this.loanType = loanType;
		}

		public String getLoanStatus() {
			return loanStatus;
		}

		public void setLoanStatus(String loanStatus) {
			this.loanStatus = loanStatus;
		}

		public BigDecimal getEmiAmount() {
			return emiAmount;
		}

		public void setEmiAmount(BigDecimal emiAmount) {
			this.emiAmount = emiAmount;
		}

		public String getLoanStartDate() {
			return loanStartDate;
		}

		public void setLoanStartDate(String loanStartDate) {
			this.loanStartDate = loanStartDate;
		}

		public String getLoanEndDate() {
			return loanEndDate;
		}

		public void setLoanEndDate(String loanEndDate) {
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

		public String getDisbrstDate() {
			return disbrstDate;
		}

		public void setDisbrstDate(String disbrstDate) {
			this.disbrstDate = disbrstDate;
		}

	    

	
}
