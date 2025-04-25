package com.example.BillingApplication.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private Double total;

//    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<InvoiceItem> items = new ArrayList<>();
    
//    public void addItem(InvoiceItem item) {
//        item.setInvoice(this); // very important!
//        this.items.add(item);
//    } 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

//	public List<InvoiceItem> getItems() {
//		return items;
//	}
//
//	public void setItems(List<InvoiceItem> items) {
//		this.items = items;
//	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}

    
}