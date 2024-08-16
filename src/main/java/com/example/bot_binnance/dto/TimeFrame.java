package com.example.bot_binnance.dto;

public enum TimeFrame {
    _5m("5m"),
    _15m("15m"),
    _30m("30m"),
    _1h("1h"),
    _2h("2h"),
    _4h("4h"),
    _6h("6h"),
    _12h("12h"),
    _1d("1d");

    private final String value;

    TimeFrame(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
