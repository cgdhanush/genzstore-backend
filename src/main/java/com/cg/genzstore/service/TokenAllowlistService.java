package com.cg.genzstore.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TokenAllowlistService {

    private Set<String> allowlist = new HashSet<>();

    public void allowlistToken(String token) {
        allowlist.add(token);
    }

    public void blacklistToken(String token) {
        allowlist.remove(token);
    }

    public boolean isAllowlisted(String token) {
        return allowlist.contains(token);
    }
}