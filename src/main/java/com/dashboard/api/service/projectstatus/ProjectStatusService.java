package com.dashboard.api.service.projectstatus;

import com.dashboard.api.domain.projectstatus.ProjectStatus;
import com.dashboard.api.persistence.jpa.projectstatus.ProjectStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class to manage {@link ProjectStatus} entities. Provides methods to retrieve project
 * status by name or ID.
 */
@Service
public class ProjectStatusService {

  private final ProjectStatusRepository projectStatusRepository;

  /**
   * Constructs a ProjectStatusService with the specified repository.
   *
   * @param projectStatusRepository repository to manage ProjectStatus entities
   */
  public ProjectStatusService(ProjectStatusRepository projectStatusRepository) {
    this.projectStatusRepository = projectStatusRepository;
  }

  /**
   * Retrieves the ID of a ProjectStatus by its name.
   *
   * @param name the name of the project status
   * @return the ID of the project status
   * @throws EntityNotFoundException if no project status with the given name is found
   */
  public int getStatusIdByName(String name) throws EntityNotFoundException {
    return projectStatusRepository
        .findByName(name)
        .orElseThrow(() -> new EntityNotFoundException(name))
        .getId();
  }

  /**
   * Finds a ProjectStatus by its ID.
   *
   * @param id the ID of the project status to find
   * @return the found ProjectStatus entity
   * @throws EntityNotFoundException if no project status with the given ID is found
   */
  public ProjectStatus findById(int id) throws EntityNotFoundException {
    return projectStatusRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Couldn't find specified status"));
  }

  /**
   * Returns the default status. Usually "ACTIVE".
   *
   * @return Default project status ID.
   * @throws EntityNotFoundException if no defaults are found.
   */
  public ProjectStatus getDefault() throws EntityNotFoundException {
    return projectStatusRepository
        .findByIsDefaultTrue()
        .orElseThrow(() -> new EntityNotFoundException("Can't find default project status"));
  }
}
