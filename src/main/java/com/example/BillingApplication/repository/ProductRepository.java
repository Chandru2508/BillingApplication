package com.example.BillingApplication.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BillingApplication.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
    

}
