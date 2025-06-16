package com.dashboard.api.service.user.dto;

import com.dashboard.api.domain.user.User;

public class UserPresenter {
    public Long id;
    public String username;

    public UserPresenter(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserPresenter from(User user) {
        return new UserPresenter(user.getId(), user.getUsername());
    }
}
