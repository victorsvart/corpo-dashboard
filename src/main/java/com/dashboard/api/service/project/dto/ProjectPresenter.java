package com.dashboard.api.service.project.dto;

import com.dashboard.api.domain.project.Project;

public class ProjectPresenter {
  public Long id;
  public String name;

  public ProjectPresenter(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static ProjectPresenter from(Project project) {
    return new ProjectPresenter(project.getId(), project.getName());
  }
}
