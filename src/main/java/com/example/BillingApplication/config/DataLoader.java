//package com.example.BillingApplication.config;
//
//import com.example.BillingApplication.model.Admin;
//import com.example.BillingApplication.repository.AdminRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Configuration
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (adminRepository.count() == 0) {
//            Admin admin = new Admin();
//            admin.setUserName("admin");
//            admin.setPassword(passwordEncoder.encode("admin123")); // Password will be stored securely
//            adminRepository.save(admin);
//            System.out.println("✅ Test Admin inserted: admin / admin123");
//        } else {
//            System.out.println("ℹ️ Admin already exists, skipping insert.");
//        }
//    }
//}