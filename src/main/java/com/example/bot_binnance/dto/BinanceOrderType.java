package com.example.bot_binnance.dto;

public enum BinanceOrderType {
    MARKET("MARKET"),
    LIMIT("LIMIT"),
    STOP("STOP"),
    STOP_MARKET("STOP_MARKET"),
    TAKE_PROFIT("TAKE_PROFIT"),
    TAKE_PROFIT_MARKET("TAKE_PROFIT_MARKET"),
    TRAILING_STOP_MARKET("TRAILING_STOP_MARKET");

    private final String value;

    BinanceOrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

