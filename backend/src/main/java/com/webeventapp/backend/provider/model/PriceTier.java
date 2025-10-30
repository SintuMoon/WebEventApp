package com.webeventapp.backend.provider.model;

public enum PriceTier {
    BUDGET("Budget"),
    MODERATE("Mittel"),
    PREMIUM("Premium"),
    LUXURY("Luxury"),
    ON_REQUEST("Preis auf Anfrage");

    private final String label;

    PriceTier(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
