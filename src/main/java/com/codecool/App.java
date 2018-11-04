package com.codecool;

import com.codecool.google.GoogleAuthorizeUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws Exception {
        GoogleAuthorizeUtil.authorize();
        SpringApplication.run(App.class, args);
    }
}
