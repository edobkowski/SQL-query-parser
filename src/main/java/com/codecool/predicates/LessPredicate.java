package com.codecool.predicates;

import java.util.function.Predicate;

public class LessPredicate implements Predicate<String> {

    private int columnNumber;
    private String expectedValue;

    public LessPredicate(int columnNumber, String expectedValue) {
        this.columnNumber = columnNumber;
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean test(String s) throws NumberFormatException {
        String[] elements = s.split("\\s*,\\s*");
        String value = elements[columnNumber].trim();
        try {
            return Double.valueOf(value) < Double.valueOf(expectedValue);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
