package com.dashboard.api.service.project.dto;

import java.util.List;
import java.util.Optional;

public class ProjectRegisterInput {
  public Optional<Long> id;
  public String name;
  public List<Long> serverIds;

  public ProjectRegisterInput(String name, List<Long> serverIds) {
    this.name = name;
    this.serverIds = serverIds;
  }
}
