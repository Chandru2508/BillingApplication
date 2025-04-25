package com.example.BillingApplication.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BillingApplication.model.Invoice;
import com.example.BillingApplication.model.Product;
import com.example.BillingApplication.repository.InvoiceRepository;
import com.example.BillingApplication.repository.ProductRepository;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepo;
    
    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private InvoiceItemService invoiceItemService;
    
    private Product product;

    @Transactional
    public void saveInvoiceWithItems(Invoice invoice) {
    	if (invoice.getDate() == null) {
            invoice.setDate(LocalDate.now());

        }

        invoiceRepo.save(invoice); // Should cascade
    }
    
    public List<Invoice> searchInvoices(Long id, String customerName, LocalDate date) {
        if (id != null) {
            return invoiceRepo.findById(id).map(List::of).orElse(List.of());
        } else if (customerName != null && !customerName.isEmpty()) {
            return invoiceRepo.findByCustomerNameContainingIgnoreCase(customerName);
        } else if (date != null) {
            return invoiceRepo.findByDate(date);
        }
        return invoiceRepo.findAll();
    }
    
    public Invoice getInvoiceById(Long id) {
        return invoiceRepo.findById(id)
                .orElse(null);
    }




}
