package com.codecool.factories;

import com.codecool.exceptions.IncorrectOperandException;
import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.helpers.enums.QueryType;
import com.codecool.models.Condition;
import com.codecool.models.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QueryFactoryTest {

    private QueryFactory queryFactory;

    @BeforeEach
    void prepareTest() {
        this.queryFactory = new QueryFactory(new ConditionFactory());
    }

    @Test
    void testProperQueryBuilding() throws IncorrectQueryException, IncorrectOperandException {
        Condition condition = new Condition("age", ">", "15");
        Query expected = new Query(QueryType.SELECT, Arrays.asList("*"), "file",
                Arrays.asList(Arrays.asList(condition)));
        Query actual = queryFactory.createQuery("select * from file where age > 15;");

        assertEquals(expected, actual);
    }

    @Test
    void passWrongQuery_ShouldThrowException() {
        assertAll(() -> {
            assertThrows(IncorrectQueryException.class, () -> {
                this.queryFactory.createQuery("");
            });
            assertThrows(IncorrectQueryException.class, () -> {
                this.queryFactory.createQuery("SELECT;");
            });
            assertThrows(IncorrectQueryException.class, () -> {
                this.queryFactory.createQuery("SELECT * from;");
            });
            assertThrows(IncorrectQueryException.class, () -> {
                this.queryFactory.createQuery("SELECT *;");
            });
            assertThrows(IncorrectQueryException.class, () -> {
                this.queryFactory.createQuery("knfnads");
            });
            assertThrows(IncorrectQueryException.class, () -> {
                this.queryFactory.createQuery("selct * from file;");
            });
            assertThrows(IncorrectQueryException.class, () -> {
                this.queryFactory.createQuery("SELECT * from file fsdjfo;"); // DOESN'T THROW EXCEPTION
            });
        });
    }
}