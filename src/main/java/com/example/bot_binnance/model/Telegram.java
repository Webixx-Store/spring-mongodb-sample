package com.example.bot_binnance.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "telegram")
public class Telegram {
	
	@Id
	 private String id;
	 private String telegramId;
	 private String publicKey;
	 private String privateKey;
	 private String userName;
	 private LocalDateTime timeCreate = LocalDateTime.now();;
	 private LocalDateTime timeUpdate ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public LocalDateTime getTimeUpdate() {
		return timeUpdate;
	}
	public void setTimeUpdate(LocalDateTime timeUpdate) {
		this.timeUpdate = timeUpdate;
	}
	public String getTelegramId() {
		return telegramId;
	}
	public void setTelegramId(String telegramId) {
		this.telegramId = telegramId;
	}
	
	
	 
	
	

}
