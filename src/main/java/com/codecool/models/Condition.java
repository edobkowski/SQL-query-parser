package com.codecool.models;

import com.codecool.exceptions.IncorrectOperandException;
import com.codecool.helpers.OperandEnum;

public class Condition {

    private String left;
    private OperandEnum operand;
    private String right;

    public Condition(String left, String operand, String right) throws IncorrectOperandException {
        this.left = left;
        this.operand = OperandEnum.getType(operand);
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public OperandEnum getOperand() {
        return operand;
    }

    public String getRight() {
        return right;
    }
}
