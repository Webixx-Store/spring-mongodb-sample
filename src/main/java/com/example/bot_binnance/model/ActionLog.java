package com.example.bot_binnance.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "action_log")
public class ActionLog {

    @Id
    private String id;
    private String date;
    private String typeOrder;
    private String orderId;
    private Double price;
	private String side; // BUY or SELL
    private String symbol;
    private Double profit = 0d;
    private String status = "";
    private LocalDateTime timeCreate;
    private LocalDateTime timeUpdate;

    // Constructors
    public ActionLog() {}

   





	// Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public LocalDateTime getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(LocalDateTime timeCreate) {
        this.timeCreate = timeCreate;
    }

    public LocalDateTime getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(LocalDateTime timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	public Double getProfit() {
		return profit;
	}



	public void setProfit(Double profit) {
		this.profit = profit;
	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
	
	
    public Double getPrice() {
		return price;
	}



	public void setPrice(Double price) {
		this.price = price;
	}







	public String getSide() {
		return side;
	}







	public void setSide(String side) {
		this.side = side;
	}







	public String getDate() {
		return date;
	}







	public void setDate(String date) {
		this.date = date;
	}







	public String getTypeOrder() {
		return typeOrder;
	}







	public void setTypeOrder(String typeOrder) {
		this.typeOrder = typeOrder;
	}
	
	
	
    
    
}