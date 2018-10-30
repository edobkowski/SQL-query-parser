package com.codecool.predicates;

import java.util.function.Predicate;

public class NotEqualsPredicate implements Predicate<String> {

    private int columnNumber;
    private String expectedValue;

    public NotEqualsPredicate(int columnNumber, String expectedValue) {
        this.columnNumber = columnNumber;
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean test(String s) {
        String[] elements = s.split("\\s*,\\s*");
        String value = elements[columnNumber].trim();

        return !value.equals(expectedValue);
    }
}
