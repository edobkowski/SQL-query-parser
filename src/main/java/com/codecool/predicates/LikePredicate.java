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

    private Pattern buildRegex() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < expectedValue.length(); i++) {
            if (expectedValue.charAt(i) == '%') sb.append("[\\w ]*");
            else if (expectedValue.charAt(i) == '_') sb.append("[\\w]{1}");
            else sb.append(expectedValue.charAt(i));
        }
        return Pattern.compile(sb.toString());
    }

    @Override
    public boolean test(String s) {
        String[] elements = s.split("\\s*,\\s*");
        String value = elements[columnNumber].trim();

        Pattern pattern = buildRegex();
        Matcher matcher = pattern.matcher(value);

        if (matcher.matches()) return true;
        return false;
    }
}
