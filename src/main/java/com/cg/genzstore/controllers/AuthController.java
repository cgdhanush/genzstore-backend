package com.cg.genzstore.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.cg.genzstore.model.dto.UserCreateDTO;
import com.cg.genzstore.model.dto.UserRequestDTO;
import com.cg.genzstore.model.entity.User;
import com.cg.genzstore.repository.UserRepository;
import com.cg.genzstore.service.JwtService;
import com.cg.genzstore.service.TokenAllowlistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenAllowlistService blacklistService;

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody UserCreateDTO requestDTO) {
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            return "Email already exists!";
        }
        // Create New User
        User newUser = User.builder()
            .name(requestDTO.getName())
            .email(requestDTO.getEmail())
            .password(passwordEncoder.encode(requestDTO.getPassword()))
            .role(requestDTO.getRole())
            .build();
            
        userRepository.save(newUser);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody UserRequestDTO requestDTO) {
        return userRepository.findByEmail(requestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(requestDTO.getPassword(), u.getPassword()))
                .map(u -> {
                    String token = jwtService.generateToken(u);
                    blacklistService.allowlistToken(token);

                    return Map.of(
                            "message", "Login successful",
                            "token", token);
                })
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String header) {

        String token = header.substring(7); // remove "Bearer "
        blacklistService.blacklistToken(token);

        return "Logged out successfully";
    }

    @GetMapping("/profile")
    public String profile(@RequestHeader("Authorization") String header) {

        String token = header.substring(7); // remove "Bearer "
        String email = jwtService.extractEmail(token);

        return "Welcome " + email;
    }

}