package com.codecool.factories;

import com.codecool.exceptions.IncorrectOperandException;
import com.codecool.models.Condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ConditionFactory {

    private List<String> splitByOr(String condition) {
        List<String> list = new ArrayList<>();
        String patternString = "([\\w <>='%]+)([^']OR[^']|[^']or[^'])([\\w <>='%]+)";
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(condition);
        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (!matcher.group(i).trim().equals("OR") && !matcher.group(i).trim().equals("or")) {
                    list.add(matcher.group(i));
                }
            }
        }
        else list.add(condition);

        return list;
    }

    private List<List<String>> getConditionsAsString(List<String> conditions) {
        List<List<String>> list = new ArrayList<>();
        String patternString = "(\\w+)[ ]?(<|>|<>|={1,2})[ ]?('\\w+'|\\w+)|(\\w+)[ ](LIKE|like){1}[ ]'([%]?\\w+[%]?)'";
        Pattern pattern = Pattern.compile(patternString);

        for (int x = 0; x < conditions.size(); x ++) {
            List<String> innerList = new ArrayList<>();
            String condition = conditions.get(x);
            Matcher matcher = pattern.matcher(condition);

            while (matcher.find()) {
                innerList.add(matcher.group());
            }
            list.add(innerList);
        }
        return list;
    }

}