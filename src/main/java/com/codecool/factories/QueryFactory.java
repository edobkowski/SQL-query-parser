package com.codecool.factories;

import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.helpers.enums.QueryType;
import com.codecool.models.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class QueryFactory {

    @Autowired
    private ConditionFactory conditionFactory;

//    public QueryFactory(ConditionFactory conditionFactory) {
//        this.conditionFactory = conditionFactory;
//    }

    public Query createQuery(String queryString) throws IncorrectQueryException {
        String patternString = "(select|SELECT|update|UPDATE|delete|DELETE|insert|INSERT)([\\w *\\.,]+)(?:[^']FROM[^']|[^']from[^'])(\\w+)(?:[^']WHERE[^']|[^']where[^'])?([\\w <>='%]+)?;";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(queryString);

        if (matcher.matches()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i <= matcher.groupCount(); i++) {
                list.add(matcher.group(i));
            }
            Query query = new Query(QueryType.getQueryType(list.get(0).trim()),
                    Arrays.asList(list.get(1).trim().split(","))
                            .stream()
                            .map(x -> x.trim())
                            .collect(Collectors.toList()),
                    list.get(2),
                    conditionFactory.getConditionList(list.get(3)));
            return query;
        }
        throw new IncorrectQueryException("Incorrect query");
    }
}
