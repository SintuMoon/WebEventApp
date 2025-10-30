package com.webeventapp.backend.provider.model;

public enum PriceTier {
    BUDGET("CHF"),
    MID_RANGE("CHF CHF"),
    PREMIUM("CHF CHF CHF"),
    ON_REQUEST("Price on request");

    private final String label;

    PriceTier(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
