package com.example.bot_binnance.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "price_log")
public class PriceLogDto {
	
	 @Id
	 private String id;
	 private Double price;
	 private String symbol;
	 private LocalDateTime timeCreate =  LocalDateTime.now();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
