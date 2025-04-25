package com.example.BillingApplication.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.BillingApplication.model.Customer;
import com.example.BillingApplication.model.Invoice;
import com.example.BillingApplication.model.InvoiceItem;
import com.example.BillingApplication.model.Product;
import com.example.BillingApplication.repository.CustomerRepository;
import com.example.BillingApplication.repository.InvoiceRepository;
import com.example.BillingApplication.repository.ProductRepository;
import com.example.BillingApplication.service.CustomerService;
import com.example.BillingApplication.service.InvoiceItemService;
import com.example.BillingApplication.service.InvoiceService;
import com.example.BillingApplication.service.ProductService;
import com.example.BillingApplication.util.InvoicePdfGenerator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private InvoiceService invoiceService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private InvoiceItemService invoiceItemService;
    
    @Autowired
    private InvoicePdfGenerator invoicePdfGenerator;

    @GetMapping
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoiceRepo.findAll());
        return "invoices";
    }

    @GetMapping("/search")
    public String searchInvoices(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        List<Invoice> invoices = invoiceService.searchInvoices(id, customerName, date);
        model.addAttribute("invoices", invoices);
        return "invoices";
    }
    
    @GetMapping("/{id}")
    public String viewInvoiceDetails(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        List<InvoiceItem> items = invoiceItemService.getItemsByInvoiceId(id); // should return List<InvoiceItem>

        if (invoice == null) {
            return "redirect:/invoices";
        }

        model.addAttribute("invoice", invoice);
        model.addAttribute("invoiceItems", items); // <- not Optional!
        return "invoice_details";
    }


    
    @GetMapping("/add")
    public String createInvoiceForm(Model model) {
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("customers", customerRepo.findAll());
        model.addAttribute("products", productRepo.findAll());
        return "invoice_form";
    }

    @PostMapping("/save")
    public String saveInvoiceAndItems(HttpServletRequest request, Model model) {
        try {
            // Save Invoice
        	Invoice invoice = new Invoice();
        	Long customerId = Long.parseLong(request.getParameter("customer.id"));
        	Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        	invoice.setCustomer(customer);
        	invoice.setDate(LocalDate.now());

        	// Parse dynamic items
        	List<InvoiceItem> items = new ArrayList<>();
        	int index = 0;
        	double grandTotal = 0.0; // <-- initialize total

        	while (true) {
        	    String productIdParam = request.getParameter("items[" + index + "].product.productId");
        	    if (productIdParam == null) break;

        	    Long productId = Long.parseLong(productIdParam);
        	    int quantity = Integer.parseInt(request.getParameter("items[" + index + "].quantity"));
        	    double price = Double.parseDouble(request.getParameter("items[" + index + "].price"));
        	    double tax = Double.parseDouble(request.getParameter("items[" + index + "].tax"));
        	    double total = Double.parseDouble(request.getParameter("items[" + index + "].total"));

        	    Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        	    InvoiceItem item = new InvoiceItem();
        	    item.setInvoice(invoice);
        	    item.setProduct(product);
        	    item.setQuantity(quantity);
        	    item.setPrice(price);
        	    item.setTax(tax);
        	    item.setTotal(total);

        	    grandTotal += total; // <-- accumulate item total
        	    items.add(item);
        	    index++;
        	}

        	invoice.setTotal(grandTotal); // <-- set invoice total
        	invoice = invoiceRepo.save(invoice); // <-- save invoice

        	for (InvoiceItem item : items) {
        	    invoiceItemService.saveInvoiceItem(invoice, item);
        	}

            return "redirect:/invoices";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "invoice_form";
        }
    }

    @GetMapping("/{id}/pdf")
    public void generatePdf(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Invoice invoice = invoiceService.getInvoiceById(id);
        List<InvoiceItem> items = invoiceItemService.getItemsByInvoiceId(id); // custom method

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + id + ".pdf");

        invoicePdfGenerator.generateInvoicePdf(invoice, items, response);
    }

    @GetMapping("/edit/{id}")
    public String editInvoice(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
        model.addAttribute("invoice", invoice);
        model.addAttribute("customers", customerRepo.findAll());
        model.addAttribute("products", productRepo.findAll());
        return "invoice_form";
    } 

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        invoiceRepo.deleteById(id);
        return "redirect:/invoices";
    }
} 
