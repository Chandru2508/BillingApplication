package com.example.BillingApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BillingApplication.model.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
	List<InvoiceItem> findByInvoiceId(Long invoiceId);

}
