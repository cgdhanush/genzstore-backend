package com.cg.genzstore.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Main {

    @GetMapping("/hello")
    public String hello(){
        return "Hello from the other side";
    }
}
