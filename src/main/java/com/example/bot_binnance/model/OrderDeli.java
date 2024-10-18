package com.example.bot_binnance.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.bot_binnance.dto.DeliStatusType;

@Document(collection = "orderDeli")
public class OrderDeli {
	
	@Id
    private String id;
	
	private String userid;
	
	private String fristName;
	private String lastName;
	private String email;
	private String addr1;
	private String addr2;
	private String country;
	private String state;
	private String city;
	private String post;

	
	private DeliStatusType deliStatus;
	
	
	private LocalDateTime createdAt = LocalDateTime.now();
	
	private LocalDateTime createdUpd = LocalDateTime.now();
	
	
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
	public String getFristName() {
		return fristName;
	}
	public void setFristName(String fristName) {
		this.fristName = fristName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getCreatedUpd() {
		return createdUpd;
	}
	public void setCreatedUpd(LocalDateTime createdUpd) {
		this.createdUpd = createdUpd;
	}
	public DeliStatusType getDeliStatus() {
		return deliStatus;
	}
	public void setDeliStatus(DeliStatusType deliStatus) {
		this.deliStatus = deliStatus;
	}
	
	
	
	
	
	

}
