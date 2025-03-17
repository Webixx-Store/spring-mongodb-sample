package com.example.bot_binnance.dto.trade;

public class BreakoutSignal {
	 public BreakoutSignal(boolean isLong, double resistance, double support) {
		super();
		this.isLong = isLong;
		this.resistance = resistance;
		this.support = support;
	}
	private boolean isLong;
     private double resistance;
     private double support;
	
     
	public boolean isLong() {
		return isLong;
	}
	public void setLong(boolean isLong) {
		this.isLong = isLong;
	}
	public double getResistance() {
		return resistance;
	}
	public void setResistance(double resistance) {
		this.resistance = resistance;
	}
	public double getSupport() {
		return support;
	}
	public void setSupport(double support) {
		this.support = support;
	}
     
}
