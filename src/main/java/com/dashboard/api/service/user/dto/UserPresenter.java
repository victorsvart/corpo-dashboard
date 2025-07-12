package com.dashboard.api.service.user.dto;

import com.dashboard.api.domain.user.User;

public class UserPresenter {
  public Long id;
  public String username;
  public String name;
  public String lastName;
  public String fullName;
  public String profilePicture;

  public UserPresenter(
      Long id, String name, String lastName, String username, String profilePicture) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.lastName = lastName;
    this.fullName = name + " " + lastName;
    this.profilePicture = profilePicture;
  }

  public static UserPresenter from(User user) {
    return new UserPresenter(
        user.getId(),
        user.getName(),
        user.getLastName(),
        user.getUsername(),
        user.getProfilePicture());
  }
}
