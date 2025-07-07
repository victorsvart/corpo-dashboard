package com.dashboard.api.persistence.seed;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.domain.server.Server;
import com.dashboard.api.domain.serverStatus.ProjectStatus;
import com.dashboard.api.persistence.jpa.project.ProjectRepository;
import com.dashboard.api.persistence.jpa.server.ServerRepository;
import com.dashboard.api.persistence.jpa.serverStatus.ProjectStatusRepository;

@Component
@Order(6)
public class ProjectSeeder implements CommandLineRunner {
  private final ProjectRepository projectRepository;
  private final ProjectStatusRepository statusRepository;
  private final ServerRepository serverRepository;

  public ProjectSeeder(ProjectRepository projectRepository, ProjectStatusRepository statusRepository,
      ServerRepository serverRepository) {
    this.projectRepository = projectRepository;
    this.statusRepository = statusRepository;
    this.serverRepository = serverRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    if (projectRepository.count() > 0)
      return;

    ProjectStatus healthyStatus = statusRepository.findByName("HEALTHY")
        .orElseThrow(() -> new IllegalStateException("Missing status: HEALTHY"));

    ProjectStatus deployingStatus = statusRepository.findByName("DEPLOYING")
        .orElseThrow(() -> new IllegalStateException("Missing status: HEALTHY"));

    List<Server> allServers = serverRepository.findAll();
    List<Server> subset = allServers.size() > 1 ? allServers.subList(0, 2) : allServers;
    Project p1 = new Project("Alpha Project", subset);
    p1.setStatus(healthyStatus);

    Project p2 = new Project("Beta Project", subset);
    p2.setStatus(deployingStatus);

    Project p3 = new Project("Gamma Project");
    p3.setStatus(healthyStatus);

    projectRepository.saveAll(List.of(p1, p2, p3));
  }
}
