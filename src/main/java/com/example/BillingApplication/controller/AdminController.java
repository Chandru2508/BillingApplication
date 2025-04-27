package com.example.BillingApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.BillingApplication.model.Invoice;
import com.example.BillingApplication.model.Product;
import com.example.BillingApplication.repository.CustomerRepository;
import com.example.BillingApplication.repository.ProductRepository;
import com.example.BillingApplication.service.ProductService;

@Controller
public class AdminController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@GetMapping("/")
	public String homePage() {
	    return "index"; // this shows index.html
	}
	
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // returns login.html (Thymeleaf template)
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
    	 List<Product> lowStockProducts = productService.getLowStockProducts();

//         if (!lowStockProducts.isEmpty()) {
//             // You can generate a message like "Product X, Product Y are below 5 in stock."
//             StringBuilder lowStockMessage = new StringBuilder("Warning: Low stock for the following products: ");
//             lowStockProducts.forEach(product -> lowStockMessage.append(product.getProductName()).append(", "));
//              lowStockMessage.delete(lowStockMessage.length() - 2, lowStockMessage.length()); // Remove the last comma
//             
//             // Add the message to the model
//             model.addAttribute("lowStockMessage", lowStockMessage.toString());
//         }
    	 model.addAttribute("invoice", new Invoice());
         model.addAttribute("customers", customerRepo.findAll());
         model.addAttribute("products", productRepo.findAll());
        return "dashboard"; // returns dashboard.html (Thymeleaf template)
    }
}
