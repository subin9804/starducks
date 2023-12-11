package org.kosta.starducks.generalAffairs.entity;

public enum ProductUnit {

    KG("KG"),
    TEN_KG("10KG"),
    HUNDRED_KG("100KG"),
    EA("EA"),
    TEN_EA("10EA"),
    HUNDRED_EA("100EA");

    private final String value;

    ProductUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
