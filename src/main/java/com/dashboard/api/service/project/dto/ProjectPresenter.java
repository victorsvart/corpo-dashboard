package com.dashboard.api.service.project.dto;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.helpers.StringUtils;
import com.dashboard.api.helpers.TimeUtils;
import com.dashboard.api.service.server.dto.ServerPresenter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** DTO class to represent project data for presentation layer. */
public class ProjectPresenter {
  public Long id;
  public String name;
  public List<ServerPresenter> servers;
  public String status;
  public String lastUpdate;
  public String details;

  /**
   * Constructs a ProjectPresenter.
   *
   * @param id project ID
   * @param name project name
   * @param servers list of associated server presenters
   * @param details project details description
   * @param status current status of the project
   * @param lastUpdated formatted last update time string
   */
  public ProjectPresenter(
      Long id,
      String name,
      List<ServerPresenter> servers,
      String details,
      String status,
      String lastUpdated) {
    this.id = id;
    this.name = name;
    this.servers = servers;
    this.status = status;
    this.details = details;
    this.lastUpdate = lastUpdated;
  }

  /**
   * Maps a Project domain entity to a ProjectPresenter DTO.
   *
   * @param project the Project entity
   * @return a ProjectPresenter representing the project
   */
  public static ProjectPresenter from(Project project) {
    List<ServerPresenter> servers = ServerPresenter.fromMany(new ArrayList<>(project.getServers()));
    return new ProjectPresenter(
        project.getId(),
        project.getName(),
        servers,
        project.getDetails(),
        StringUtils.capitalizeWord(project.getStatusName()),
        TimeUtils.formatRelativeTime(project.getUpdatedAt(), Instant.now()));
  }

  /**
   * Maps a list of Project entities to a list of ProjectPresenter DTOs.
   *
   * @param projects the list of Project entities
   * @return a list of ProjectPresenter objects
   */
  public static List<ProjectPresenter> fromMany(List<Project> projects) {
    return projects.stream().map(ProjectPresenter::from).collect(Collectors.toList());
  }
}
