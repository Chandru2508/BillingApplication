package com.example.BillingApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class TestController {
	 @GetMapping("/")
	    public String home() {
	        return "Spring Boot is working!";
	    }
}
