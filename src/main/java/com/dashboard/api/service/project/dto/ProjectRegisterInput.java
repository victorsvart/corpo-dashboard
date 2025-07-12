package com.dashboard.api.service.project.dto;

import java.util.List;
import java.util.Optional;

/** Data transfer object for registering or updating a project. Contains project details. */
public class ProjectRegisterInput {
  public Optional<Long> id;
  public String name;
  public List<Long> serverIds;
  public String details;

  /**
   * Constructor for creating a new project input without an ID.
   *
   * @param name the project name
   * @param serverIds list of server IDs associated with the project
   * @param details additional project details or description
   */
  public ProjectRegisterInput(String name, List<Long> serverIds, String details) {
    this.name = name;
    this.serverIds = serverIds;
    this.details = details;
  }
}
