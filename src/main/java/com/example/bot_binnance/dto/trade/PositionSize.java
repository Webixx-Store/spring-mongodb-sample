package com.example.bot_binnance.dto.trade;

public class PositionSize {
	 private double size;
     private double riskAmount;
     private double priceDistance;
 	public PositionSize(double size, double riskAmount, double priceDistance) {
		super();
		this.size = size;
		this.riskAmount = riskAmount;
		this.priceDistance = priceDistance;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}

	public double getRiskAmount() {
		return riskAmount;
	}
	public void setRiskAmount(double riskAmount) {
		this.riskAmount = riskAmount;
	}
	public double getPriceDistance() {
		return priceDistance;
	}
	public void setPriceDistance(double priceDistance) {
		this.priceDistance = priceDistance;
	}
     
     
}
