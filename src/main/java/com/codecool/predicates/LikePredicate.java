package com.codecool.predicates;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LikePredicate implements Predicate<String> {

    private int columnNumber;
    private String expectedValue;

    public LikePredicate(int columnNumber, String expectedValue) {
        this.columnNumber = columnNumber;
        this.expectedValue = expectedValue;
    }

}
