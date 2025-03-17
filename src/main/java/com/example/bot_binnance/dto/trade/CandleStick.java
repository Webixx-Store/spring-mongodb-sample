package com.example.bot_binnance.dto.trade;


import java.time.LocalDateTime;

public class CandleStick {
    private long openTime;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private long closeTime;
    private double quoteAssetVolume;
    private int numberOfTrades;
    private double takerBuyBaseAssetVolume;
    private double takerBuyQuoteAssetVolume;

    // Constructor
    public CandleStick(long openTime, double open, double high, double low, double close, double volume,
                        long closeTime, double quoteAssetVolume, int numberOfTrades, 
                        double takerBuyBaseAssetVolume, double takerBuyQuoteAssetVolume) {
        this.openTime = openTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.closeTime = closeTime;
        this.quoteAssetVolume = quoteAssetVolume;
        this.numberOfTrades = numberOfTrades;
        this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
        this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
    }

    // Getters and Setters
    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public double getQuoteAssetVolume() {
        return quoteAssetVolume;
    }

    public void setQuoteAssetVolume(double quoteAssetVolume) {
        this.quoteAssetVolume = quoteAssetVolume;
    }

    public int getNumberOfTrades() {
        return numberOfTrades;
    }

    public void setNumberOfTrades(int numberOfTrades) {
        this.numberOfTrades = numberOfTrades;
    }

    public double getTakerBuyBaseAssetVolume() {
        return takerBuyBaseAssetVolume;
    }

    public void setTakerBuyBaseAssetVolume(double takerBuyBaseAssetVolume) {
        this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
    }

    public double getTakerBuyQuoteAssetVolume() {
        return takerBuyQuoteAssetVolume;
    }

    public void setTakerBuyQuoteAssetVolume(double takerBuyQuoteAssetVolume) {
        this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
    }

    // Convert openTime to LocalDateTime
    public LocalDateTime getOpenDateTime() {
        return LocalDateTime.ofEpochSecond(openTime / 1000, 0, java.time.ZoneOffset.UTC);
    }
    
    // Convert closeTime to LocalDateTime
    public LocalDateTime getCloseDateTime() {
        return LocalDateTime.ofEpochSecond(closeTime / 1000, 0, java.time.ZoneOffset.UTC);
    }

    @Override
    public String toString() {
        return "CandleStick{" +
                "openTime=" + openTime +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", closeTime=" + closeTime +
                ", numberOfTrades=" + numberOfTrades +
                '}';
    }
}
