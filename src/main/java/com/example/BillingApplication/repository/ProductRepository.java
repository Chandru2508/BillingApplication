package com.example.BillingApplication.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BillingApplication.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameContainingIgnoreCase(String keyword);

	boolean existsByProductName(String productName);

	Optional<Product> findByProductName(String productName);
    

}
