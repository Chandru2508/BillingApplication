package com.example.BillingApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, unique = true)
    private long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    private String category;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "quantity_in_stock", nullable = false)
    private Integer quantityInStock;

    @Column(name = "tax_percent")
    private Double taxPercent;

    @Column(length = 500)
    private String description;

    // --- Constructors ---

    public Product() {}

    public Product(String productName, String category,
                   Double unitPrice, Integer quantityInStock,
                   Double taxPercent, String description) {
        this.productName = productName;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantityInStock = quantityInStock;
        this.taxPercent = taxPercent;
        this.description = description;
    }

    // --- Getters & Setters ---

    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(Double taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
