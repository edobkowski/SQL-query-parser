package com.codecool.predicates;

import com.codecool.FileHandler;
import com.codecool.exceptions.FileHandlerException;
import com.codecool.exceptions.IncorrectOperandException;
import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.models.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Unit test for simple PredicateFactory.
 */
public class PredicateFactoryTest {

    PredicateFactory predicateFactory;
    Stream<String> data;

    @BeforeEach
    void init() throws FileHandlerException {
        String fileName = "src/main/resources/testFile.csv";
        FileHandler fileHandler = new FileHandler();
        String header = fileHandler.getHeader(fileName);
        this.data = fileHandler.getDataStream(fileName);
        this.predicateFactory = new PredicateFactory(header);
    }

    @Test
    void getResultSet_SingleCondition() throws IncorrectQueryException, IncorrectOperandException {
        List<List<Condition>> conditions = new ArrayList(Arrays.asList(new ArrayList(Arrays.asList(new Condition(
                "name", "=",
         "Eliza")))));
        Predicate<String> predicate = this.predicateFactory.getPredicate(conditions);
        List<String> expectedResult = new ArrayList<>(Arrays.asList("Eliza, Pistacjowa, 30, F",
                "Eliza, Laskowa, 30, F"));
        List<String> actualResult = data.filter(predicate).collect(Collectors.toList());

        assertAll(() -> {
            assertTrue(expectedResult.containsAll(actualResult));
            assertTrue(actualResult.containsAll(expectedResult));
        });
    }

    @Test
    void getResultSet_WithOrAnd1() throws IncorrectQueryException, IncorrectOperandException {
        List<List<Condition>> conditions = this.getCond1();
        Predicate<String> predicate = this.predicateFactory.getPredicate(conditions);

        List<String> expectedResult = getExpectedResultForCond1();
        List<String> actualResult = data.filter(predicate).collect(Collectors.toList());

        assertAll(() -> {
            assertTrue(expectedResult.containsAll(actualResult));
            assertTrue(actualResult.containsAll(expectedResult));
        });
    }

    @Test
    void getResultSet_WithOrAnd2() throws IncorrectQueryException, IncorrectOperandException {
        List<List<Condition>> conditions = this.getCond2();
        Predicate<String> predicate = this.predicateFactory.getPredicate(conditions);

        List<String> expectedResult = getExpectedResultForCond2();
        List<String> actualResult = data.filter(predicate).collect(Collectors.toList());

        assertAll(() -> {
            assertTrue(expectedResult.containsAll(actualResult));
            assertTrue(actualResult.containsAll(expectedResult));
        });
    }

    @Test
    void getResultSet_LikeCondition() throws IncorrectQueryException, IncorrectOperandException {
        List<List<Condition>> conditions = new ArrayList(Arrays.asList(new ArrayList(Arrays.asList(new Condition(
                "name", "LIKE",
                "%li%")))));

        Predicate<String> predicate = this.predicateFactory.getPredicate(conditions);
        List<String> expectedResult = new ArrayList<>(Arrays.asList("Eliza, Pistacjowa, 30, F",
                "Eliza, Laskowa, 30, F", "Filip, Brzozowski, 8, M"));
        List<String> actualResult = data.filter(predicate).collect(Collectors.toList());

        assertAll(() -> {
            assertTrue(expectedResult.containsAll(actualResult));
            assertTrue(actualResult.containsAll(expectedResult));
        });
    }

    @Test
    void getResultSet_LikeCondition2() throws IncorrectQueryException, IncorrectOperandException {
        List<List<Condition>> conditions = new ArrayList(Arrays.asList(new ArrayList(Arrays.asList(new Condition(
                "name", "like",
                "__li%")))));

        Predicate<String> predicate = this.predicateFactory.getPredicate(conditions);
        List<String> expectedResult = new ArrayList<>(Arrays.asList("Filip, Brzozowski, 8, M"));
        List<String> actualResult = data.filter(predicate).collect(Collectors.toList());

        assertAll(() -> {
            assertTrue(expectedResult.containsAll(actualResult));
            assertTrue(actualResult.containsAll(expectedResult));
        });
    }

    private List<List<Condition>> getCond1() throws IncorrectOperandException {
        List<Condition> group1 = new ArrayList<>(Arrays.asList(
                new Condition("age", ">", "35"),
                new Condition("age", "<", "60")));
        List<Condition> group2 = new ArrayList<>(Arrays.asList(
                new Condition("sex", "=", "F")
        ));

        return new ArrayList<List<Condition>>(Arrays.asList(group1, group2));
    }

    private List<List<Condition>> getCond2() throws IncorrectOperandException {
        List<Condition> group1 = new ArrayList<>(Arrays.asList(
                new Condition("age", ">", "35"),
                new Condition("age", "<", "60")));
        List<Condition> group2 = new ArrayList<>(Arrays.asList(
                new Condition("sex", "=", "F"),
                new Condition("name", "=", "Eliza")
        ));

        return new ArrayList<List<Condition>>(Arrays.asList(group1, group2));
    }

    private List<String> getExpectedResultForCond1() {
        return new ArrayList<>(Arrays.asList(
                "Stanisław, Lagunowski, 40, M",
                "Tomasz, Pies, 54, M",
                "Eliza, Pistacjowa, 30, F",
                "Eliza, Laskowa, 30, F",
                "Agnieszka, Biegunowska, 68, F"
        ));
    }

    private List<String> getExpectedResultForCond2() {
        return new ArrayList<>(Arrays.asList(
                "Stanisław, Lagunowski, 40, M",
                "Tomasz, Pies, 54, M",
                "Eliza, Pistacjowa, 30, F",
                "Eliza, Laskowa, 30, F"
        ));
    }
}
