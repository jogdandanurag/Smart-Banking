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
@Table(name = "tbl_banking_transcation")
public class TransactionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private long accountId; // Reference to the account associated with this transaction
    
	private String transactionType; // CREDIT or DEBIT
    
	private BigDecimal amount;
    
	private String currency; // e.g., USD, INR, EUR
    
	private TransactionStatus transactionStatus; // PENDING, SUCCESS, FAILED
    
	private PaymentMethod paymentMethod; // e.g., UPI, NET BANKING, CREDIT CARD
    
	private String description; // Description of transaction
    
	private Timestamp transactionDate;
    
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

		public long getAccountId() {
			return accountId;
		}

		public void setAccountId(long accountId) {
			this.accountId = accountId;
		}

		public String getTransactionType() {
			return transactionType;
		}

		public void setTransactionType(String transactionType) {
			this.transactionType = transactionType;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public TransactionStatus getTransactionStatus() {
			return transactionStatus;
		}

		public void setTransactionStatus(TransactionStatus transactionStatus) {
			this.transactionStatus = transactionStatus;
		}

		public PaymentMethod getPaymentMethod() {
			return paymentMethod;
		}

		public void setPaymentMethod(PaymentMethod paymentMethod) {
			this.paymentMethod = paymentMethod;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Timestamp getTransactionDate() {
			return transactionDate;
		}

		public void setTransactionDate(Timestamp transactionDate) {
			this.transactionDate = transactionDate;
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

