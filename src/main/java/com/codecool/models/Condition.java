package com.codecool.models;

import com.codecool.exceptions.IncorrectOperandException;
import com.codecool.helpers.OperandEnum;

public class Condition {

    private String left;
    private OperandEnum operator;
    private String right;

    public Condition(String left, String operator, String right) throws IncorrectOperandException {
        this.left = left;
        this.operator = OperandEnum.getType(operator);
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public OperandEnum getOperator() {
        return operator;
    }

    public String getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }
}
