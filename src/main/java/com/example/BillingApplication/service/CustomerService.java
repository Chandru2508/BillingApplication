package com.example.BillingApplication.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BillingApplication.model.Customer;
import com.example.BillingApplication.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public List<Customer> search(String name, Long id, String mobile, LocalDate createdAt) {
        if (name != null && !name.isEmpty()) {
            return customerRepo.findByNameContainingIgnoreCase(name);
        } else if (mobile != null && !mobile.isEmpty()) {
            return customerRepo.findByMobile(mobile);
        } else if (id != null) {
            return customerRepo.findById(id).map(List::of).orElse(List.of());
        } else if (createdAt != null) {
            return customerRepo.findByCreatedAt(createdAt);
        } else {
            return customerRepo.findAll();
        }
    }
    
    public void saveCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> result = customerRepo.findById(id);
        return result.orElse(null);
    }

    public void deleteCustomer(Long id) {
        customerRepo.deleteById(id);
    }

    public List<Customer> searchByName(String name) {
        return customerRepo.findByNameContainingIgnoreCase(name);
    }

    public List<Customer> searchByMobile(String mobile) {
        return customerRepo.findByMobile(mobile);
    }
}
