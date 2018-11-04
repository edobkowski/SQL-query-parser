package com.codecool.controllers;

import com.codecool.exceptions.DataReaderException;
import com.codecool.exceptions.IncorrectQueryException;
import com.codecool.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private QueryService queryService;

    @GetMapping("/")
    public String handleGet(Model model) {
        model.addAttribute("records", "");

        return "index";
    }

    @PostMapping("/")
    public String handlePost(Model model, @RequestParam("stringQuery") String stringQuery) throws IncorrectQueryException, DataReaderException {
        List<String> resultSet = this.queryService.executeQuery(stringQuery);
        model.addAttribute("records", resultSet);

        return "index";
    }
}
