package com.dashboard.api.service.user.dto;

/**
 * Data transfer object representing the input for updating a user's profile.
 *
 * @param firstName the updated first name of the user
 * @param lastName the updated last name of the user
 */
public record UpdateUserInput(String firstName, String lastName) {}
