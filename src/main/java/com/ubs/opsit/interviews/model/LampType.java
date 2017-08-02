package com.ubs.opsit.interviews.model;

public enum LampType {
    RED_LIGHT("R"), YELLOW_LIGHT("Y"), UNLIT("O");

    LampType(String symbol) {
        this.symbol = symbol;
    }

    private String symbol;

    public String getSymbol() {
        return symbol;
    }
}