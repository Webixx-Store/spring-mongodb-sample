package com.example.bot_binnance.dto.trade;

public class TradingLevels {
	 private double stopLoss;
     private double takeProfit;
     private double trailingStop;
	public double getStopLoss() {
		return stopLoss;
	}
	public void setStopLoss(double stopLoss) {
		this.stopLoss = stopLoss;
	}
	public double getTakeProfit() {
		return takeProfit;
	}
	public void setTakeProfit(double takeProfit) {
		this.takeProfit = takeProfit;
	}
	public double getTrailingStop() {
		return trailingStop;
	}
	public void setTrailingStop(double trailingStop) {
		this.trailingStop = trailingStop;
	}
     
}
