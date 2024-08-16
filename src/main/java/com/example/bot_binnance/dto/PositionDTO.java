package com.example.bot_binnance.dto;

public class PositionDTO {
    private String symbol;
    private double positionAmt;
    private double entryPrice;
    private double breakEvenPrice;
    private double markPrice;
    private double unRealizedProfit;
    private double liquidationPrice;
    private int leverage;
    private double maxNotionalValue;
    private String marginType;
    private double isolatedMargin;
    private boolean isAutoAddMargin;
    private String positionSide;
    private double notional;
    private double isolatedWallet;
    private long updateTime;
    private boolean isolated;
    private int adlQuantile;

    // Getters and setters for each field
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPositionAmt() {
        return positionAmt;
    }

    public void setPositionAmt(double positionAmt) {
        this.positionAmt = positionAmt;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public double getBreakEvenPrice() {
        return breakEvenPrice;
    }

    public void setBreakEvenPrice(double breakEvenPrice) {
        this.breakEvenPrice = breakEvenPrice;
    }

    public double getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(double markPrice) {
        this.markPrice = markPrice;
    }

    public double getUnRealizedProfit() {
        return unRealizedProfit;
    }

    public void setUnRealizedProfit(double unRealizedProfit) {
        this.unRealizedProfit = unRealizedProfit;
    }

    public double getLiquidationPrice() {
        return liquidationPrice;
    }

    public void setLiquidationPrice(double liquidationPrice) {
        this.liquidationPrice = liquidationPrice;
    }

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public double getMaxNotionalValue() {
        return maxNotionalValue;
    }

    public void setMaxNotionalValue(double maxNotionalValue) {
        this.maxNotionalValue = maxNotionalValue;
    }

    public String getMarginType() {
        return marginType;
    }

    public void setMarginType(String marginType) {
        this.marginType = marginType;
    }

    public double getIsolatedMargin() {
        return isolatedMargin;
    }

    public void setIsolatedMargin(double isolatedMargin) {
        this.isolatedMargin = isolatedMargin;
    }

    public boolean isAutoAddMargin() {
        return isAutoAddMargin;
    }

    public void setAutoAddMargin(boolean autoAddMargin) {
        isAutoAddMargin = autoAddMargin;
    }

    public String getPositionSide() {
        return positionSide;
    }

    public void setPositionSide(String positionSide) {
        this.positionSide = positionSide;
    }

    public double getNotional() {
        return notional;
    }

    public void setNotional(double notional) {
        this.notional = notional;
    }

    public double getIsolatedWallet() {
        return isolatedWallet;
    }

    public void setIsolatedWallet(double isolatedWallet) {
        this.isolatedWallet = isolatedWallet;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isIsolated() {
        return isolated;
    }

    public void setIsolated(boolean isolated) {
        this.isolated = isolated;
    }

    public int getAdlQuantile() {
        return adlQuantile;
    }

    public void setAdlQuantile(int adlQuantile) {
        this.adlQuantile = adlQuantile;
    }
}
