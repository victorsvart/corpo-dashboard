package com.dashboard.api.service.user.dto;

import com.dashboard.api.domain.authority.Authority;
import java.util.Set;

/**
 * Data transfer object representing the payload for user registration.
 *
 * @param username the desired username for the new user
 * @param password the raw password chosen by the user
 * @param firstName the user's first name
 * @param lastName the user's last name
 * @param authorities the set of authorities (roles/permissions) assigned to the user; may be empty
 *     or null
 */
public record RegisterRequest(
    String username,
    String password,
    String firstName,
    String lastName,
    Set<Authority> authorities) {}
