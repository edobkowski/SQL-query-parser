package com.codecool.factories;

import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.models.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryFactoryTest {

    private ConditionFactory conditionFactory = new ConditionFactory();
    private QueryFactory queryFactory;

    @BeforeEach
    void prepareTest() {
        this.queryFactory = new QueryFactory(conditionFactory);
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