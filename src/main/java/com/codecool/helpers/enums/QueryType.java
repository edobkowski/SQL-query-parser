package com.codecool.helpers.enums;

import com.codecool.exceptions.IncorrectQueryException;

public enum QueryType {

    SELECT,
    UPDATE,
    INSERT,
    DELETE;

    public static QueryType getQueryType(String query) throws IncorrectQueryException {
        switch (query) {
            case "select": {
                return SELECT;
            }
            case "SELECT": {
                return SELECT;
            }
            case "update": {
                return UPDATE;
            }
            case "UPDATE": {
                return UPDATE;
            }
            case "insert": {
                return INSERT;
            }
            case "INSERT": {
                return INSERT;
            }
            case "delete": {
                return DELETE;
            }
            case "DELETE": {
                return DELETE;
            }
            default: throw new IncorrectQueryException("Incorrect query");
        }
    }
}
