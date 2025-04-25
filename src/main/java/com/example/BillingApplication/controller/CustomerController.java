package com.example.BillingApplication.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.BillingApplication.model.Customer;
import com.example.BillingApplication.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;

    public CustomerController (CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer_form";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customer_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    @GetMapping
    public String viewCustomers(Model model) {
    	model.addAttribute("customers", customerService.getAllCustomers());
    	return "customers";
    }

    @GetMapping("/searchCustomers")
    public String listCustomers(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String mobile,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdAt, Model model) {

        List<Customer> customers = customerService.search(name, id, mobile, createdAt);
        model.addAttribute("customers", customers);
        return "customers";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Customer> results;
        if ("name".equalsIgnoreCase(keyword)) {
            results = customerService.searchByName(keyword);
        } else {
            results = customerService.searchByMobile(keyword);
        }
        model.addAttribute("customers", results);
        return "customers";
    }
}