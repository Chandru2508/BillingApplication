package com.example.BillingApplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.BillingApplication.model.Product;
import com.example.BillingApplication.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public List<Product> searchByNameOrId(String keyword) {
        List<Product> result = new ArrayList<>();

        // Search by name (string search)
        result.addAll(repo.findByProductNameContainingIgnoreCase(keyword));

        // If the keyword is numeric, try searching by ID
        try {
            Long id = Long.valueOf(keyword);
            repo.findById(id).ifPresent(result::add);
        } catch (NumberFormatException e) {;
            // Not a valid ID; skip searching by ID
        }
        return result;
        
    }

    
    public Product save(Product product) {
        return repo.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Product> search(String keyword) {
        return repo.findByProductNameContainingIgnoreCase(keyword);
    }
}
