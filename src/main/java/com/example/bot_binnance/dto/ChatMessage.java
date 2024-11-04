package com.example.bot_binnance.dto;

public class ChatMessage {
	String message;
	String user;
	String image;
	boolean flag = false;
	
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
	public ChatMessage(String message, String user , String image , boolean flag) {
		super();
		this.message = message;
		this.user = user;
		this.image = image;
		this.flag = flag;
	}
	public ChatMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	
	

	
}
