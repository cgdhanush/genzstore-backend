package com.cg.genzstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.cg.genzstore.model.entity.User;
import com.cg.genzstore.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

   @Autowired
    private PasswordEncoder passwordEncoder;

    // Signup endpoint
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already exists!";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    // Login endpoint
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userRepository.findByEmail(user.getEmail())
                .map(u -> passwordEncoder.matches(user.getPassword(), u.getPassword())
                        ? "Login successful"
                        : "Invalid password")
                .orElse("User not found");
    }
}