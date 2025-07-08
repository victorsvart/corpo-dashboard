package com.dashboard.api.service.project.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.helpers.StringUtils;
import com.dashboard.api.helpers.TimeUtils;
import com.dashboard.api.service.server.dto.ServerPresenter;

public class ProjectPresenter {
  public Long id;
  public String name;
  public List<ServerPresenter> servers;
  public String status;
  public String lastUpdate;
  public String details;

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

  public static ProjectPresenter from(Project project) {
    List<ServerPresenter> servers = ServerPresenter.fromMany(new ArrayList<>(project.getServers()));
    return new ProjectPresenter(
        project.getId(),
        project.getName(),
        servers,
        project.getDetails(),
        StringUtils.capitalizeWord(project.getStatusName()),
        TimeUtils.formatRelativeTime(project.getUpdatedAt(), LocalDateTime.now()));

  }

  public static List<ProjectPresenter> fromMany(List<Project> servers) {
    return servers.stream()
        .map(ProjectPresenter::from)
        .collect(Collectors.toList());
  }
}
