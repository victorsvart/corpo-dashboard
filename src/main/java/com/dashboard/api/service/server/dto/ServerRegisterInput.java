package com.dashboard.api.service.server.dto;

import java.util.Optional;

public class ServerRegisterInput {
  public Optional<Long> id;
  public String name;

  public ServerRegisterInput() {
  }

  public ServerRegisterInput(String name) {
    this.name = name;

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
