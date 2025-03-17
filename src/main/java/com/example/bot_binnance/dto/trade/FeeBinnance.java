package com.example.bot_binnance.dto.trade;

public class FeeBinnance {
	private double buyOpen;
	private double buyClose;
	private double profit;
	private double totalFees;
	public double getBuyOpen() {
		return buyOpen;
	}
	public void setBuyOpen(double buyOpen) {
		this.buyOpen = buyOpen;
	}
	public double getBuyClose() {
		return buyClose;
	}
	public void setBuyClose(double buyClose) {
		this.buyClose = buyClose;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(double totalFees) {
		this.totalFees = totalFees;
	}
	public FeeBinnance(double buyOpen, double buyClose, double profit, double totalFees) {
		super();
		this.buyOpen = buyOpen;
		this.buyClose = buyClose;
		this.profit = profit;
		this.totalFees = totalFees;
	}
	@Override
	public String toString() {
	    return "FeeBinnance{" +
	            "buyOpen=" + buyOpen +
	            ", buyClose=" + buyClose +
	            ", profit=" + profit +
	            ", totalFees=" + totalFees +
	            '}';
	}

	
	
	
}
