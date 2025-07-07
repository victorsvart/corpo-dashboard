package com.dashboard.api.application.controllers.project;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.service.project.ProjectService;
import com.dashboard.api.service.project.dto.ProjectPresenter;
import com.dashboard.api.service.project.dto.ProjectRegisterInput;

@RestController
@EnableMethodSecurity
@RequestMapping("/project")
public class ProjectController {

  private ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping("/getAll")
  @PreAuthorize("hasRole('USER')")
  public List<ProjectPresenter> getAll() {
    List<Project> projects = projectService.getAll();
    return ProjectPresenter.fromMany(projects);
  }

  @GetMapping("/get")
  @PreAuthorize("hasRole('USER')")
  public ProjectPresenter get(@RequestParam Long id) {
    return projectService.get(id);
  }

  @PostMapping("/register")
  @PreAuthorize("hasRole('USER')")
  public ProjectPresenter register(@RequestBody ProjectRegisterInput input) {
    Project project = projectService.register(input);
    return ProjectPresenter.from(project);
  }

  @PutMapping("/update")
  @PreAuthorize("hasRole('USER')")
  public ProjectPresenter update(@RequestBody ProjectRegisterInput input) {
    Project project = projectService.update(input);
    return ProjectPresenter.from(project);
  }

  @DeleteMapping("/delete")
  @PreAuthorize("hasRole('USER')")
  public String delete(@RequestParam Long id) {
    return projectService.delete(id);
  }
}
