package com.example.BillingApplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BillingApplication.model.Invoice;
import com.example.BillingApplication.model.InvoiceItem;
import com.example.BillingApplication.model.Product;
import com.example.BillingApplication.repository.InvoiceItemRepository;
import com.example.BillingApplication.repository.ProductRepository;

@Service
public class InvoiceItemService {

    @Autowired
    private InvoiceItemRepository invoiceItemRepo;

    @Autowired
    private ProductRepository productRepo;

    @Transactional
    public void saveInvoiceItem(Invoice invoice, InvoiceItem item) {
        Product dbProduct = productRepo.findById(item.getProduct().getProductId())
            .orElseThrow(() -> new RuntimeException("Invalid product in item"));

        if (dbProduct.getQuantityInStock() < item.getQuantity()) {
            throw new RuntimeException("Insufficient stock for " + dbProduct.getProductName());
        }

        // Update stock
        dbProduct.setQuantityInStock(dbProduct.getQuantityInStock() - item.getQuantity());
        productRepo.save(dbProduct);

        // Calculate totals
        double subtotal = item.getQuantity() * dbProduct.getUnitPrice();
        double taxAmount = subtotal * (dbProduct.getTaxPercent() / 100);
        double total = subtotal + taxAmount;

        item.setInvoice(invoice); // Link to saved invoice
        item.setProduct(dbProduct); // link to loaded product
        item.setPrice(dbProduct.getUnitPrice());
        item.setTax(dbProduct.getTaxPercent());
        item.setTotal(total);

        invoiceItemRepo.save(item);
    }

	public Optional<InvoiceItem> getInvoiceById(Long id) {
		// TODO Auto-generated method stub
		return invoiceItemRepo.findById(id);
	}

	public List<InvoiceItem> getItemsByInvoiceId(Long invoiceId) {
		// TODO Auto-generated method stub
		return invoiceItemRepo.findByInvoiceId(invoiceId);
	}
}
