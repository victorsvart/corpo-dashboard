package com.dashboard.api.service.user.dto;

public record UserWithTokenPresenter(UserPresenter user, String token) {}
