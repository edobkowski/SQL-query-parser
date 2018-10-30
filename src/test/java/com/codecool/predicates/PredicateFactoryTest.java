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

    PredicateFactory predicateFactory;

    @BeforeEach
    void init() throws FileHandlerException {
        String fileName = "src/main/resources/testFile.csv";
        FileHandler fileHandler = new FileHandler();
        String header = fileHandler.getHeader(fileName);
        Stream<String> data = fileHandler.getDataStream(fileName);
        this.predicateFactory = new PredicateFactory(header);
    }

    @Test
    void getPredicates() throws IncorrectQueryException {
        Predicate<String> equalsPredicate = this.predicateFactory.getPredicate("age = 10");
        Predicate<String> greaterThanPredicate = this.predicateFactory.getPredicate("age > 10");
        Predicate<String> lessThanPredicate = this.predicateFactory.getPredicate("age < 10");

        assertAll(() -> {
            assertTrue(equalsPredicate instanceof EqualsPredicate);
            assertTrue(greaterThanPredicate instanceof GreaterPredicate);
            assertTrue(lessThanPredicate instanceof LessPredicate);
        });
    }

    @Test
    void getPredicateWithWrongQuery_SholdThrowIncorrectQueryException() throws IncorrectQueryException {
        assertThrows(IncorrectQueryException.class, () -> {
            this.predicateFactory.getPredicate("age 10");
        });
    }
}
