package com.codecool.controllers;

import com.codecool.exceptions.FileHandlerException;
import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.factories.ConditionFactory;
import com.codecool.models.Condition;
import com.codecool.predicates.PredicateFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@SpringBootApplication
public class MainController {

    private PredicateFactory predicateFactory;
    private Stream<String> data;
    private ConditionFactory conditionFactory;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainController.class, args);
    }

    @GetMapping("/")
    public String handleGet(Model model) {
        model.addAttribute("records", "");

        return "index";
    }

    @PostMapping("/")
    public String handlePost(Model model, @RequestParam("stringQuery") String stringQuery) throws IncorrectQueryException, FileHandlerException {
        init();
        List<List<Condition>> conditions = this.conditionFactory.getConditionList(stringQuery);
        Predicate<String> predicate = this.predicateFactory.getPredicate(conditions);
        List<String> resultSet = this.data.filter(predicate).collect(Collectors.toList());
        model.addAttribute("records", resultSet);

        return "index";
    }

    private void init() throws FileHandlerException {
        String fileName = "src/main/resources/testFile.csv";
        com.codecool.FileHandler fileHandler = new com.codecool.FileHandler();
        String header = fileHandler.getHeader(fileName);
        this.data = fileHandler.getDataStream(fileName);
        this.predicateFactory = new PredicateFactory(header);
        this.conditionFactory = new ConditionFactory();
    }
}
