package com.example.bot_binnance.model;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;

public class ProductRewiew {
	@Id
    private String id;
	
	@NotBlank(message = "User id is mandatory")
	private String userid;
	
	@NotBlank(message = "Product id is mandatory")
	private String productid;
	
	
	private int rating = 0;
	
	private String cmt = "";

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
	
	
	
	

}
