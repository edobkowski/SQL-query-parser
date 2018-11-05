package com.codecool.services;

import com.codecool.exceptions.DataReaderException;
import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.factories.QueryFactory;
import com.codecool.helpers.readers.DataReader;
import com.codecool.models.Query;
import com.codecool.factories.PredicateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class QueryService {

    @Autowired
    private QueryFactory queryFactory;
    private DataReader dataReader;
    private Map<String, Integer> mappedColumns = new HashMap<>();


    public List<String> executeQuery(String stringQuery) throws IncorrectQueryException, DataReaderException {
        Query query = this.queryFactory.createQuery(stringQuery);
        this.dataReader.setSource(query.getSource());
        List<String> header = this.dataReader.getHeader();
        getMap(header);
        PredicateFactory predicateFactory = new PredicateFactory(mappedColumns);
        Stream<String> data = this.dataReader.getDataStream();

        return data.filter(query.getListOfConditions().isEmpty() ? p -> true : predicateFactory.getPredicate(query.getListOfConditions()))
                .map(line -> Arrays.asList(line.split("\\s*,\\s*")))
                .map(x -> getRequestColumns(x, query.getColumnList(), this.mappedColumns))
                .map(x -> String.join(", ", x))
                .collect(Collectors.toList());
    }

    public void getMap(List<String> header) {
        int i = 0;
        for (String column : header) {
            this.mappedColumns.put(column, i++);
        }
    }

    public List<String> getRequestColumns(List<String> x, List<String> columns, Map<String, Integer> map) {
        List<String> list = new ArrayList<>();

        if (columns.contains("*")) return x;
        for (int i = 0; i < columns.size(); i++) {
            int colNumber = map.get(columns.get(i));
            list.add(x.get(colNumber));
        }
        return list;
    }

    public boolean dataReaderNotSet() {
        return this.dataReader == null;
    }

    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }
}
