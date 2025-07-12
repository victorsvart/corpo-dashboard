package com.dashboard.api.service.user.dto;

import com.dashboard.api.domain.authority.Authority;
import java.util.Set;

public record RegisterRequest(
    String username,
    String password,
    String firstName,
    String lastName,
    Set<Authority> authorities) {}
