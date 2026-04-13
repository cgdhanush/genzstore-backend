package com.cg.genzstore.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.cg.genzstore.model.dto.ApiResponse;
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
    public ResponseEntity<?> signup(@Valid @RequestBody UserCreateDTO requestDTO) {

        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Email already exists!"));
        }

        User newUser = User.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .role(requestDTO.getRole())
                .build();

        userRepository.save(newUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse("User registered successfully!"));
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
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization", required = false) String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Missing or invalid Authorization header"));
        }

        String token = header.substring(7);
        blacklistService.blacklistToken(token);

        return ResponseEntity.ok(new ApiResponse("Logged out successfully"));
    }

    @GetMapping("/profile")
    public Map<String, Object> profile(@RequestHeader("Authorization") String header) {

        String token = header.substring(7); // remove "Bearer "
        String email = jwtService.extractEmail(token);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome");
        response.put("email", email);

        return response;
    }

}