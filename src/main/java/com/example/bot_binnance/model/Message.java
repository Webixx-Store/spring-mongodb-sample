package com.example.bot_binnance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages") 
public class Message {

    @Id
    private String id; 

    private String userId;
    private String userName;
    private String qMessage;
    private String aMessage;
    private String qMuti;
    private String aMuti;
    private LocalDateTime createdAt;

    // Constructor
    public Message() {
    	this.createdAt = LocalDateTime.now();// Tự động gán thời gian tạo
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQMessage() {
        return qMessage;
    }

    public void setQMessage(String qMessage) {
        this.qMessage = qMessage;
    }

    public String getAMessage() {
        return aMessage;
    }

    public void setAMessage(String aMessage) {
        this.aMessage = aMessage;
    }

    public String getQMuti() {
        return qMuti;
    }

    public void setQMuti(String qMuti) {
        this.qMuti = qMuti;
    }

    public String getAMuti() {
        return aMuti;
    }

    public void setAMuti(String aMuti) {
        this.aMuti = aMuti;
    }

    public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}