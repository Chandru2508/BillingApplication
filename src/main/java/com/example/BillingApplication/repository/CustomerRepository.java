package com.example.BillingApplication.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BillingApplication.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByMobile(String mobile);
	List<Customer> findByNameContainingIgnoreCase(String name);
	List<Customer> findByCreatedAt(LocalDate date);

}
