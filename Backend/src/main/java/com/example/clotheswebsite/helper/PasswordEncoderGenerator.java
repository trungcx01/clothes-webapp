package com.example.clotheswebsite.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderGenerator {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("admin"));
        //$2a$10$jUMJ6pB2A8s22WXjg2Da5uJbJPmr0vfrMUzObsYPsjl3mo5GnZ5Wy
    }
}