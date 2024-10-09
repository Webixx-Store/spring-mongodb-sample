package com.example.bot_binnance.dto;

import java.time.LocalDateTime;	

public class ProductRewiewDto {
	
	 private String id;
	 private String userid;
	 private String productid;
	 private int rating = 0;
	 private String cmt = "";
	 private LocalDateTime createdAt = LocalDateTime.now();
	 
	 private String imageName;

    // getters and setters
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
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
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	 
	 
	 
	 
	 
}
