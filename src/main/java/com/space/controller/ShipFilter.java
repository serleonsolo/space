package com.space.controller;

public enum ShipFilter
{
    NAME("name"),
    PLANET("planet"),
    SHIP_TYPE("shipType"),
    AFTER("after"),
    BEFORE("before"),
    IS_USED("isUsed"),
    MIN_SPEED("minSpeed"),
    MAX_SPEED("maxSpeed"),
    MIN_CREW_SIZE("minCrewSize"),
    MAX_CREW_SIZE("maxCrewSize"),
    MIN_RATING("minRating"),
    MAX_RATING("maxRating"),
    ORDER("order"),
    PAGE_NUMBER("pageNumber"),
    PAGE_SIZE("pageSize"),
    ;

    private String fieldName;

    ShipFilter(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return fieldName;
    }
}
