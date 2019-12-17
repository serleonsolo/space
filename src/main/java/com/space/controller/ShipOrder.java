package com.space.controller;

public enum ShipOrder {
    ID("id"), // default
    SPEED("speed"),
    DATE("prodDate"),
    RATING("rating");

    private String fieldName;

    ShipOrder(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static ShipOrder getType(String input) {
        for (ShipOrder type: ShipOrder.values()) {
            if (type.toString().equals(input)) {
                return type;
            }
        }
        throw new RuntimeException("unknown type");
    }
}