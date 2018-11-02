package com.codecool.predicates;


import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.models.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateFactory
{
    private Map<String, Integer> columns = new HashMap<>();

    public PredicateFactory(List<String> header) {
        int i = 0;
        for (String column : header) {
            this.columns.put(column, i++);
        }
    }
    public Predicate<String> getPredicate(List<List<Condition>> conditions) throws IncorrectQueryException {

        List<Predicate<String>> orConditions = new ArrayList<>();

        for(List<Condition> conditionList : conditions) {
            orConditions.add(chainPredicatesWithAnd(conditionList.stream()
                    .map(this::getPredicateForCondition)
                    .collect(Collectors.toList())));
        }

        return chainPredicatesWithOr(orConditions);
    }

    private Predicate<String> chainPredicatesWithAnd (List<Predicate<String>> predicates) {
        Predicate<String> root = predicates.get(0);
        predicates.remove(0);
        return predicates.stream()
                .reduce(root, (p1, p2) -> p1.and(p2));
    }

    private Predicate<String> chainPredicatesWithOr (List<Predicate<String>> predicates) {
        Predicate<String> root = predicates.get(0);
        predicates.remove(0);
        return predicates.stream()
                .reduce(root, (p1, p2) -> p1.or(p2));
    }

    private Predicate<String> getPredicateForCondition(Condition condition) {
        String leftOperand = condition.getLeft();
        String rightOperand = condition.getRight();
        int columnNumber = this.columns.get(leftOperand);
        switch (condition.getOperator()) {
            case EQUALS: return new EqualsPredicate(columnNumber, rightOperand);
            case NOT_EQUAL: return new NotEqualsPredicate(columnNumber, rightOperand);
            case GREATER_THAN: return new GreaterPredicate(columnNumber, rightOperand);
            case LESS_THEN: return new LessPredicate(columnNumber, rightOperand);
            case GREATER_OR_EQUALS: return new GreaterOrEqualsPredicate(columnNumber, rightOperand);
            case LESS_OR_EQUALS: return new LessOrEqualsPredicate(columnNumber, rightOperand);
            case LIKE: return new LikePredicate(columnNumber, rightOperand);
            default: return null;
        }
    }
}
