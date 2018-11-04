package com.codecool.services;

import com.codecool.exceptions.DataReaderException;
import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.factories.QueryFactory;
import com.codecool.helpers.readers.DataReader;
import com.codecool.models.Query;
import com.codecool.predicates.PredicateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class QueryService {

    @Autowired
    private QueryFactory queryFactory;
    private DataReader dataReader;

    public List<String> executeQuery(String stringQuery) throws IncorrectQueryException, DataReaderException {
        Query query = this.queryFactory.createQuery(stringQuery);
        this.dataReader.setSource(query.getSource());
        List<String> columns = this.dataReader.getHeader();
        PredicateFactory predicateFactory = new PredicateFactory(columns);
        Predicate<String> predicate = predicateFactory.getPredicate(query.getListOfConditions());
        Stream<String> data = this.dataReader.getDataStream();
        return data.filter(predicate).collect(Collectors.toList());
    }

    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }
}
