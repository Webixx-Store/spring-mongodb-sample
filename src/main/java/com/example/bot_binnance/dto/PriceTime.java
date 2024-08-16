package com.example.bot_binnance.dto;

import com.example.bot_binnance.service.BackTest;

public class PriceTime {
	public long time;
	public double price;
	
	public String getTime() {
		return BackTest.formatTime(this.time);
	}
	
	public PriceTime(long time , double price){
		this.time = time;
		this.price = price;
	}
	
}