package com.example.BillingApplication.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.BillingApplication.repository.CustomerRepository;
import com.example.BillingApplication.repository.ProductRepository;

@Controller
@RequestMapping("/dashboardCon")
public class DashboardController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ProductRepository productRepo;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("customers", customerRepo.findAll());
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("today", LocalDate.now());
        return "dashboard";
    }
}
