package com.example.BillingApplication.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.BillingApplication.model.Product;
import com.example.BillingApplication.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    
    // @Autowired
    // private ProductRepository repo;

    public ProductController(ProductService service) {
        this.service = service;
    }

//    @GetMapping
//    public String listProducts(@RequestParam(value = "search", required = false) String search, Model model) {
//        List<Product> products;
//    	if (search != null && !search.isEmpty()) {
//        	products = service.search(search);
//            model.addAttribute("products", products);
//            if(products.isEmpty()) {
//            	model.addAttribute("error", "No Matching Found For the Keyword :" + search);
//            }
//        } else {
//            model.addAttribute("products", service.getAll());
//        }
//        return "products";
//    }
    
    @GetMapping
    public String listProducts1(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = service.searchByNameOrId(keyword);
            if(products.isEmpty()) {
            	model.addAttribute("error", "No Matching Found For the Keyword :" + keyword);
            }
        } else {
            products = service.getAll();
        }
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword); // to preserve in the input
        return "products";
    }

    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        service.saveOrUpdate(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = service.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Product ID"));
        model.addAttribute("product", product);
        return "product_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/products";
    }
}
