package com.dashboard.api.persistence.seed;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.dashboard.api.persistence.jpa.server.ServerRepository;
import com.dashboard.api.service.project.ProjectService;
import com.dashboard.api.service.project.dto.ProjectRegisterInput;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Component
@Order(5)
public class ProjectSeeder implements CommandLineRunner {
  private final ProjectService projectService;
  private final ServerRepository serverRepository;

  public ProjectSeeder(ProjectService projectService,
      ServerRepository serverRepository) {
    this.projectService = projectService;
    this.serverRepository = serverRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    try {
      Long server1Id = serverRepository.getByName("SERVER-001")
          .orElseThrow(() -> new Exception("internal seeding error"))
          .getId();

      Long server2Id = serverRepository.getByName("SERVER-002")
          .orElseThrow(() -> new Exception("internal seeding error"))
          .getId();

      Long server3Id = serverRepository.getByName("SERVER-003")
          .orElseThrow(() -> new Exception("internal seeding error"))
          .getId();

      List<ProjectRegisterInput> projects = List.of(
          new ProjectRegisterInput("PROJECT-ALPHA", List.of(server1Id)),
          new ProjectRegisterInput("PROJECT-BETA", List.of(server2Id)),
          new ProjectRegisterInput("PROJECT-GAMMA", List.of(server1Id, server3Id)));

      for (ProjectRegisterInput project : projects) {
        try {
          projectService.register(project);
          System.out.println("Registered project: " + project.name);
        } catch (EntityExistsException e) {
          System.out.println("Project already exists or server already assigned: " + project.name);
        } catch (EntityNotFoundException e) {
          System.out.println("One or more servers not found for project: " + project.name);
        }
      }
    } catch (Exception e) {
      System.out.println("Unexpected error while seeding projects: " + e.getMessage());
    }
  }
}
