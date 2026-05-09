package com.cg.genzstore.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.genzstore.model.dto.UserDTO;
import com.cg.genzstore.model.entity.User;
import com.cg.genzstore.service.JwtService;
import com.cg.genzstore.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/profile")
    public Map<String, Object> profile(@RequestHeader("Authorization") String header) {

        String token = header.substring(7); // remove "Bearer "
        String email = jwtService.extractEmail(token);

        User user = userService.getUserByEmail(email); // fetch from DB

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome");
        response.put("email", email);
        response.put("username", user.getName());

        return response;
    }

}
