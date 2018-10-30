package com.codecool.predicates;

import com.codecool.exceptions.IncorrectQueryException;

import java.util.function.Predicate;

public class GreaterPredicate implements Predicate<String> {

    private int columnNumber;
    private String expectedValue;

    public GreaterPredicate(int columnNumber, String expectedValue) {
        this.columnNumber = columnNumber;
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean test(String s) throws NumberFormatException {
        String[] elements = s.split("\\s*,\\s*");
        String value = elements[columnNumber].trim();
        try {
            return Double.valueOf(value) > Double.valueOf(expectedValue);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Wrong value type for arithmetical ");
        }
    }
}
