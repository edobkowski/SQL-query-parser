package com.codecool;

import com.codecool.exceptions.FileHandlerException;
import com.codecool.exceptions.IncorrectOperandException;
import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.factories.ConditionFactory;
import com.codecool.models.Condition;
import com.codecool.predicates.PredicateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {

    private PredicateFactory predicateFactory;
    private Stream<String> data;
    private ConditionFactory conditionFactory;

    @BeforeEach
    void init() throws FileHandlerException {
        String fileName = "src/main/resources/testFile.csv";
        com.codecool.FileHandler fileHandler = new com.codecool.FileHandler();
        String header = fileHandler.getHeader(fileName);
        this.data = fileHandler.getDataStream(fileName);
        this.predicateFactory = new PredicateFactory(header);
        this.conditionFactory = new ConditionFactory();
    }

    @Test
    void getResultSet_SingleCondition() throws IncorrectQueryException, IncorrectOperandException {
        String condition = "age > 30 and age < 60 or name = 'Filip'";
        List<List<Condition>> conditions = conditionFactory.getConditionList(condition);
        Predicate<String> predicate = this.predicateFactory.getPredicate(conditions);
        List<String> expectedResult = new ArrayList<>(Arrays.asList("Stanisław, Lagunowski, 40, M",
                "Tomasz, Pies, 54, M", "Filip, Brzozowski, 8, M"));
        List<String> actualResult = data.filter(predicate).collect(Collectors.toList());

        actualResult.forEach(System.out::println);
        for(List<Condition> list : conditions) {
            list.forEach(System.out::println);
        }

        assertAll(() -> {
            assertTrue(expectedResult.containsAll(actualResult));
            assertTrue(actualResult.containsAll(expectedResult));
        });
    }
}
