package com.codecool.models;

import com.codecool.helpers.enums.QueryType;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Query {

    private QueryType queryType;
    private List<String> columnList;
    private boolean isAll;
    private List<List<Condition>> listOfConditions;
    private String source;

    public Query(QueryType queryType, List<String> columnList, String source, List<List<Condition>> listOfConditions) {
        this.queryType = queryType;
        if (columnList.contains("*")) isAll = true;
        this.columnList = columnList;
        this.listOfConditions = listOfConditions;
        this.source = source;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<String> columnList) {
        this.columnList = columnList;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public List<List<Condition>> getListOfConditions() {
        return listOfConditions;
    }

    public void setListOfConditions(List<List<Condition>> listOfConditions) {
        this.listOfConditions = listOfConditions;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        String s = listOfConditions.stream().flatMap(Collection::stream).collect(Collectors.toList()).toString();
        return queryType + (isAll ? "*" : columnList.stream().collect(Collectors.joining(", ", " ", " "))) + "FROM " + source + (listOfConditions.isEmpty() ? "" : " WHERE ")
                + (listOfConditions.isEmpty() ? ";" : s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query = (Query) o;
        return isAll == query.isAll &&
                queryType == query.queryType &&
                Objects.equals(columnList, query.columnList) &&
                Objects.equals(listOfConditions, query.listOfConditions) &&
                Objects.equals(source, query.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryType, columnList, isAll, listOfConditions, source);
    }
}
