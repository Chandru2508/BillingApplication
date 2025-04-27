package com.example.BillingApplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void saveOrUpdate(Product product) {
        if (product.getProductId() != null) {
            // Case 1: Update by ID
            Product existing = repo.findById(product.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product ID: " + product.getProductId()));

            existing.setProductName(product.getProductName());
            existing.setCategory(product.getCategory());
            existing.setUnitPrice(product.getUnitPrice());
            existing.setQuantityInStock(product.getQuantityInStock());
            existing.setTaxPercent(product.getTaxPercent());
            existing.setDescription(product.getDescription());
            // Hibernate auto-updates because of @Transactional
        } else {
            // Case 2: No ID given - check by productName
            Optional<Product> existingProduct = repo.findByProductName(product.getProductName());
            
            if (existingProduct.isPresent()) {
                // Update the existing product (matched by name)
                Product existing = existingProduct.get();
                existing.setCategory(product.getCategory());
                existing.setUnitPrice(product.getUnitPrice());
                existing.setQuantityInStock(product.getQuantityInStock());
                existing.setTaxPercent(product.getTaxPercent());
                existing.setDescription(product.getDescription());
                // Hibernate auto-updates
            } else {
                // New product — Save it
                repo.save(product);
            }
        }
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
    
    public List<Product> getLowStockProducts() {
        return repo.findAll().stream()
                .filter(product -> product.getQuantityInStock() < 5)
                .collect(Collectors.toList());
    }

}
