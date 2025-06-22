package com.dashboard.api.service.user.dto;

import java.util.Set;

import com.dashboard.api.domain.authority.Authority;

public record RegisterRequest(String username, String password, String firstName, String lastName,
    Set<Authority> authorities) {
}
