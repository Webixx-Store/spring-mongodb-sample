package com.example.bot_binnance.dto;

public enum DeliStatusType {
	
	CONFIRMED("CONFIRMED"),
	PACKING("PACKING"),
	SHIPPING("SHIPPING"),
	DELIVERED("DELIVERED"),
	COMPLETED("COMPLETED");

    private final String value;

    DeliStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

