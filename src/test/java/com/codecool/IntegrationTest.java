package com.codecool;

import com.codecool.exceptions.DataReaderException;
import com.codecool.exceptions.IncorrectOperandException;
import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.factories.ConditionFactory;
import com.codecool.helpers.readers.FileReader;
import com.codecool.models.Condition;
import com.codecool.factories.PredicateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {

    private PredicateFactory predicateFactory;
    private Stream<String> data;
    private ConditionFactory conditionFactory;
    private Map<String, Integer> mappedColumns = new HashMap<>();


    @BeforeEach
    void init() throws DataReaderException {
        String fileName = "testFile";
        FileReader fileReader = new FileReader();
        fileReader.setSource(fileName);
        List<String> header = fileReader.getHeader();
        getMap(header);
        this.data = fileReader.getDataStream();
        this.predicateFactory = new PredicateFactory(this.mappedColumns);
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

        assertAll(() -> {
            assertTrue(expectedResult.containsAll(actualResult));
            assertTrue(actualResult.containsAll(expectedResult));
        });
    }

    private void getMap(List<String> header) {
        int i = 0;
        for (String column : header) {
            this.mappedColumns.put(column, i++);
        }
    }
}
