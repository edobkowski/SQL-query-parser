package com.codecool.predicates;


import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.predicates.EqualsPredicate;
import com.codecool.predicates.GreaterPredicate;
import com.codecool.predicates.LessPredicate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Hello world!
 *
 */
public class PredicateFactory
{

    private Map<String, Integer> columns = new HashMap<>();

    PredicateFactory(String header) {
        String[] columnNames = header.split("\\s*,\\s*");
        int i = 0;

        for (String column : columnNames) {
            this.columns.put(column, i++);
        }
    }
    public Predicate<String> getPredicate(String conditions) throws IncorrectQueryException {
        Map<String, String> conditionParameters = parseCondition(conditions);
        int columnNumber = this.columns.get(conditionParameters.get("left"));
        String expectedValue = conditionParameters.get("right");

        switch (conditionParameters.get("operation")) {
            case "=": return new EqualsPredicate(columnNumber, expectedValue);
            case ">": return new GreaterPredicate(columnNumber, expectedValue);
            case "<": return new LessPredicate(columnNumber, expectedValue);
            default: throw new IncorrectQueryException("Incorrect logical operator");
        }
    }

    private Map<String, String> parseCondition(String conditions) throws IncorrectQueryException {
        Map<String, String> conditionParameters = new HashMap<>();
        String[] elements = conditions.split(" ");
        if(elements.length != 3) {
            throw new IncorrectQueryException("Wrong query conditions: " + conditions);
        }

        conditionParameters.put("left", elements[0]);
        conditionParameters.put("operation", elements[1]);
        conditionParameters.put("right", elements[2]);

        return conditionParameters;
    }
}
