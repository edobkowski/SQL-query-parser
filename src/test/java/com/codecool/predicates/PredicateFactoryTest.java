package com.codecool.predicates;

import com.codecool.FileHandler;
import com.codecool.exceptions.FileHandlerException;
import com.codecool.exceptions.IncorrectQueryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Unit test for simple PredicateFactory.
 */
public class PredicateFactoryTest {

    private String header;
    private Stream<String> data;

    @BeforeEach
    void init() throws FileHandlerException {
        String fileName = "src/main/resources/testFile.csv";
        FileHandler fileHandler = new FileHandler();
        this.header = fileHandler.getHeader(fileName);
        this.data = fileHandler.getDataStream(fileName);
    }

    @Test
    void getPredicates() throws IncorrectQueryException {
        PredicateFactory predicateFactory = new PredicateFactory(header);
        Predicate<String> equalsPredicate = predicateFactory.getPredicate("age = 10");
        Predicate<String> greaterThanPredicate = predicateFactory.getPredicate("age > 10");
        Predicate<String> lessThanPredicate = predicateFactory.getPredicate("age < 10");

        assertAll(() -> {
            assertTrue(equalsPredicate instanceof EqualsPredicate);
            assertTrue(greaterThanPredicate instanceof GreaterPredicate);
            assertTrue(lessThanPredicate instanceof LessPredicate);
        });
    }
}
