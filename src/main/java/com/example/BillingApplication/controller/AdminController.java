package com.example.BillingApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@GetMapping("/")
	public String homePage() {
	    return "index"; // this shows index.html
	}
	
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // returns login.html (Thymeleaf template)
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // returns dashboard.html (Thymeleaf template)
    }
}
