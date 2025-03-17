package com.example.bot_binnance.dto.trade;

import java.util.Map;

public class TradingSignal {
	 private boolean isLong;
     private double entryPrice;
     private TradingLevels levels;
     private PositionSize position;
     private Map<String, Double> indicators;
     
	public boolean isLong() {
		return isLong;
	}
	public void setLong(boolean isLong) {
		this.isLong = isLong;
	}
	public double getEntryPrice() {
		return entryPrice;
	}
	public void setEntryPrice(double entryPrice) {
		this.entryPrice = entryPrice;
	}
	public TradingLevels getLevels() {
		return levels;
	}
	public void setLevels(TradingLevels levels) {
		this.levels = levels;
	}
	public PositionSize getPosition() {
		return position;
	}
	public void setPosition(PositionSize position) {
		this.position = position;
	}
	public Map<String, Double> getIndicators() {
		return indicators;
	}
	public void setIndicators(Map<String, Double> indicators) {
		this.indicators = indicators;
	}
}
