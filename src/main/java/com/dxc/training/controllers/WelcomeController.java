package com.dxc.training.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class WelcomeController {
    @GetMapping
    public String sayHello() {
        return "Welcome from global, please login or register";
    }
}
