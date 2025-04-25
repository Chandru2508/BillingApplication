//package com.example.BillingApplication.controller;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(RuntimeException.class)
//    public String handleRuntimeException(RuntimeException ex, Model model) {
//        ex.printStackTrace(); // Print full stack trace to console/logs
//        model.addAttribute("message", ex.getMessage());
//        return "error"; // This will look for templates/error.html
//    }
//
//    @ExceptionHandler(Exception.class)
//    public String handleGenericException(Exception ex, Model model) {
//        ex.printStackTrace();
//        model.addAttribute("message", "Unexpected error occurred: " + ex.getMessage());
//        return "error";
//    }
//}
