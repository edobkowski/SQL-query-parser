package com.codecool.factories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConditionFactoryTest {

    @Test
    void getConditionList() {
        ConditionFactory conditionFactory = new ConditionFactory();
        String condition = buildCondition();
        System.out.println(condition);
        int expectedSize = 5;
        int actualSize = conditionFactory.getConditionList(condition).size();

        assertEquals(expectedSize, actualSize);
    }

    private String buildCondition() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++) {
            sb.append("cond" + i + " = 10");
            if(i == 9) {
                sb.append(";");
            } else if(i%2 == 0) {
                sb.append(" and ");
            } else {
                sb.append(" or ");
            }
        }

        return sb.toString();
    }
}