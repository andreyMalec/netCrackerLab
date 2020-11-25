package com.malec.netCrackerLab.validator;

public enum Conditions {
    EQUALS("equals to"), GREATER_THAN("greater than"), LESS_THAN(
            "less than"), GREATER_THAN_OR_EQUALS("greater than or equals to"), LESS_THAN_OR_EQUALS(
            "less than or equals to");

    private final String value;

    Conditions(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
