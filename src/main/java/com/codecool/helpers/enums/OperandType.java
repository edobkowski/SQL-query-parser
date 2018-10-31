package com.codecool.helpers.enums;

import com.codecool.exceptions.IncorrectOperandException;

public enum OperandType {

    EQUALS("="),
    GREATER_THAN(">"),
    LESS_THEN("<"),
    GREATER_OR_EQUALS(">="),
    LESS_OR_EQUALS("<="),
    NOT_EQUAL("<>"),
    LIKE("LIKE");

    private String value;

    OperandType(String value) {
        this.value = value;
    }

    public static OperandType getType(String value) throws IncorrectOperandException {
        switch (value) {
            case "=": {
                return EQUALS;
            }
            case ">": {
                return GREATER_THAN;
            }
            case "<": {
                return LESS_THEN;
            }
            case ">=": {
                return GREATER_OR_EQUALS;
            }
            case "<=": {
                return LESS_OR_EQUALS;
            }
            case "<>": {
                return NOT_EQUAL;
            }
            case "LIKE": {
                return LIKE;
            }
            case "like": {
                return LIKE;
            }
            default: throw new IncorrectOperandException("Incorrect operand exception");
        }
    }

    public String getValue() {
        return value;
    }
}
