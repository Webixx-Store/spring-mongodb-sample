package com.example.bot_binnance.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
@Document(collection = "paymentMethods")
public class PaymentMethod {
	  @Id
	    private String id;

	    private String userid;

	    @NotBlank(message = "Payment type is mandatory")
	    private String type;

	    private String provider;
	    private String cardNumber;  // For credit card payments
	    private String expiryDate;  // For credit card payments
	    private String bankName;    // For bank transfer payments
	    private String accountNumber;  // For bank transfer payments
	    private String swiftCode;  // For bank transfer payments
	    private String billingAddress;
	    private LocalDateTime createdAt = LocalDateTime.now();

	    // Getters và Setters

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	

	    public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}

		public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public String getProvider() {
	        return provider;
	    }

	    public void setProvider(String provider) {
	        this.provider = provider;
	    }

	    public String getCardNumber() {
	        return cardNumber;
	    }

	    public void setCardNumber(String cardNumber) {
	        this.cardNumber = cardNumber;
	    }

	    public String getExpiryDate() {
	        return expiryDate;
	    }

	    public void setExpiryDate(String expiryDate) {
	        this.expiryDate = expiryDate;
	    }

	    public String getBankName() {
	        return bankName;
	    }

	    public void setBankName(String bankName) {
	        this.bankName = bankName;
	    }

	    public String getAccountNumber() {
	        return accountNumber;
	    }

	    public void setAccountNumber(String accountNumber) {
	        this.accountNumber = accountNumber;
	    }

	    public String getSwiftCode() {
	        return swiftCode;
	    }

	    public void setSwiftCode(String swiftCode) {
	        this.swiftCode = swiftCode;
	    }

	    public String getBillingAddress() {
	        return billingAddress;
	    }

	    public void setBillingAddress(String billingAddress) {
	        this.billingAddress = billingAddress;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }
}
