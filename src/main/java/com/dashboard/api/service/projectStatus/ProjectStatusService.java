package com.dashboard.api.service.projectStatus;

import org.springframework.stereotype.Service;

import com.dashboard.api.domain.serverStatus.ProjectStatus;
import com.dashboard.api.persistence.jpa.serverStatus.ProjectStatusRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectStatusService {
  private final ProjectStatusRepository projectStatusRepository;

  public ProjectStatusService(ProjectStatusRepository serverStatusRepository) {
    this.projectStatusRepository = serverStatusRepository;
  }

  public int getStatusIdByName(String name) throws EntityNotFoundException {
    return projectStatusRepository.findByName(name)
        .orElseThrow(() -> new EntityNotFoundException(name))
        .getId();
  }

  public ProjectStatus findById(int id) throws EntityNotFoundException {
    return projectStatusRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("Couldn't find specified status"));
  }
}
