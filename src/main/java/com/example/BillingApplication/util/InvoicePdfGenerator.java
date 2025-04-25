package com.example.BillingApplication.util;

import java.awt.Color;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BillingApplication.model.Invoice;
import com.example.BillingApplication.model.InvoiceItem;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class InvoicePdfGenerator {

    public void generateInvoicePdf(Invoice invoice, List<InvoiceItem> items, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + invoice.getId() + ".pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Font tableFont = new Font(Font.HELVETICA, 12);

        // Invoice Header
        document.add(new Paragraph("Invoice #" + invoice.getId(), titleFont));
        document.add(new Paragraph("Date: " + invoice.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        document.add(new Paragraph("Customer: " + invoice.getCustomer().getName()));
        document.add(Chunk.NEWLINE);

        // Invoice Items Table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3f, 1f, 2f, 2f});

        addTableHeader(table, tableFont, "Product", "Quantity", "Price (₹)", "Total (₹)");

        for (InvoiceItem item : items) {
            table.addCell(new PdfPCell(new Phrase(item.getProduct().getProductName(), tableFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getQuantity()), tableFont)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f", item.getPrice()), tableFont)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f", item.getTotal()), tableFont)));
        }

        document.add(table);
        document.add(Chunk.NEWLINE);

        // Total
        Paragraph total = new Paragraph("Total Amount: ₹" + invoice.getTotal(), titleFont);
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        document.close();
    }

    private void addTableHeader(PdfPTable table, Font font, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, font));
            headerCell.setBackgroundColor(Color.LIGHT_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }
    }
}
