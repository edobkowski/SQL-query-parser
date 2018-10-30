package com.codecool.helpers;

public enum OperandEnum {

    EQUALS("="),
    GREATER_THAN(">"),
    LESS_THEN("<"),
    GREATER_OR_EQUALS(">="),
    LESS_OR_EQUALS("<="),
    NOT_EQUAL("<>"),
    LIKE("LIKE");

    private String value;

    OperandEnum(String value) {
        this.value = value;
    }
}
