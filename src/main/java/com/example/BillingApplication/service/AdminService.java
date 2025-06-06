package com.example.BillingApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.BillingApplication.model.Admin;
import com.example.BillingApplication.repository.AdminRepository; // Replace with actual package

@Service
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        return User.builder()
            .username(admin.getUserName())
            .password(admin.getPassword())
            .roles("ADMIN")
            .build();
    }
}
