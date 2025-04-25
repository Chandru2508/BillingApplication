package com.example.BillingApplication.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BillingApplication.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

//	@Query("SELECT i FROM Invoice i " +
//		       "WHERE str(i.id) LIKE %:keyword% " +
//		       "OR LOWER(i.customer.name) LIKE LOWER(concat('%', :keyword, '%')) " +
//		       "OR str(i.date) LIKE %:keyword%")
//		List<Invoice> findByIdOrCustomerNameOrDate(@Param("keyword") String keyword);
	
	List<Invoice> findByCustomerNameContainingIgnoreCase(String name);
	List<Invoice> findByDate(LocalDate date);

}
