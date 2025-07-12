package com.dashboard.api.service.project;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.domain.server.Server;
import com.dashboard.api.persistence.jpa.project.ProjectRepository;
import com.dashboard.api.service.base.BaseService;
import com.dashboard.api.service.mapper.Mapper;
import com.dashboard.api.service.project.dto.ProjectPresenter;
import com.dashboard.api.service.project.dto.ProjectRegisterInput;
import com.dashboard.api.service.server.ServerService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing {@link Project} entities. Implements CRUD operations and
 * business logic related to projects, including validation against servers assigned to projects.
 */
@Service
public class ProjectService
    implements BaseService<Project, ProjectPresenter, ProjectRegisterInput> {

  private ProjectRepository projectRepository;
  private ServerService serverService;

  /**
   * Constructs a ProjectService with the specified repository and server service.
   *
   * @param projectRepository the repository used to manage projects persistence
   * @param serverService the service used to manage servers related to projects
   */
  public ProjectService(ProjectRepository projectRepository, ServerService serverService) {
    this.projectRepository = projectRepository;
    this.serverService = serverService;
  }

  /**
   * Checks if any of the given server IDs are already registered in a project.
   *
   * @param projectName the name of the project to check
   * @param serverIds the list of server IDs to verify
   * @return true if any server is already registered in the project, false otherwise
   */
  private boolean isAnyServerRegisteredInProject(String projectName, List<Long> serverIds) {
    Optional<Long> projectId = projectRepository.findIdByName(projectName);
    if (projectId.isPresent()
        && projectRepository.existsByNameAndServerIds(projectId.get(), serverIds)) {
      return true;
    }

    return false;
  }

  /**
   * Retrieves all projects.
   *
   * @return a list of all projects
   */
  public List<Project> getAll() {
    return projectRepository.findAll();
  }

  /**
   * Retrieves a project by its ID.
   *
   * @param id the ID of the project to retrieve
   * @return a presenter DTO of the project
   * @throws EntityNotFoundException if no project with the specified ID is found
   */
  public ProjectPresenter get(Long id) throws EntityNotFoundException {
    Project project =
        projectRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Project not found"));

    return ProjectPresenter.from(project);
  }

  /**
   * Registers a new project with the given input data.
   *
   * @param input the input data containing project details and server IDs
   * @return the newly created Project entity
   * @throws EntityExistsException i
   * @throws EntityNotFoundException if any of the specified servers are not found
   */
  public Project register(ProjectRegisterInput input)
      throws EntityExistsException, EntityNotFoundException {
    if (projectRepository.exists(Example.of(new Project(input.name)))) {
      throw new EntityExistsException("There already a project with the specified name!");
    }

    List<Server> serversSelected = serverService.getMany(input.serverIds);
    if (serversSelected.isEmpty()) {
      throw new EntityNotFoundException("Server not found");
    }

    if (isAnyServerRegisteredInProject(input.name, input.serverIds)) {
      throw new EntityExistsException("Server is already registered in project");
    }
    Project project = Mapper.from(input, serversSelected);
    return projectRepository.save(project);
  }

  /**
   * Updates an existing project with the given input data.
   *
   * @param input the input data containing updated project details
   * @return the updated Project entity
   * @throws IllegalArgumentException if the input does not contain an ID
   * @throws EntityNotFoundException if no project with the specified ID is found
   */
  public Project update(ProjectRegisterInput input) throws IllegalArgumentException {
    if (input.id.isEmpty()) {
      throw new IllegalArgumentException("id is required!");
    }

    Project project =
        projectRepository
            .findById(input.id.get())
            .orElseThrow(() -> new EntityNotFoundException("Couldn't find specified project"));

    Mapper.fromTo(input, project);
    return projectRepository.save(project);
  }

  /**
   * Deletes a project by its ID.
   *
   * @param id the ID of the project to delete
   * @return a confirmation message
   */
  public String delete(Long id) {
    projectRepository.deleteById(id);
    return "Deleted sucessfully";
  }
}
