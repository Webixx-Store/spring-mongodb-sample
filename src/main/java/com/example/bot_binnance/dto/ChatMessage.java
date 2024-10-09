package com.example.bot_binnance.dto;

public class ChatMessage {
	String message;
	String user;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public ChatMessage(String message, String user) {
		super();
		this.message = message;
		this.user = user;
	}
	public ChatMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
}
