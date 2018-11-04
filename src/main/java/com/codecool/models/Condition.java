package com.codecool.models;

import com.codecool.exceptions.IncorrectOperandException;
import com.codecool.helpers.enums.OperandType;

import java.util.Objects;

public class Condition {

    private String left;
    private OperandType operator;
    private String right;

    public Condition(String left, String operator, String right) throws IncorrectOperandException {
        this.left = left;
        this.operator = OperandType.getType(operator);
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public OperandType getOperator() {
        return operator;
    }

    public String getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left + " " + operator.getValue() + " " + right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return Objects.equals(left, condition.left) &&
                operator == condition.operator &&
                Objects.equals(right, condition.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, operator, right);
    }
}
