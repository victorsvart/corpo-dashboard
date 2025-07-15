package com.dashboard.api.service.project;

import com.dashboard.api.domain.entitymanager.EntityManagerHelper;
import com.dashboard.api.domain.project.Project;
import com.dashboard.api.domain.projectstatus.ProjectStatus;
import com.dashboard.api.domain.server.Server;
import com.dashboard.api.persistence.jpa.project.ProjectRepository;
import com.dashboard.api.service.base.BaseService;
import com.dashboard.api.service.project.dto.ProjectPresenter;
import com.dashboard.api.service.project.dto.ProjectRegisterInput;
import com.dashboard.api.service.projectstatus.ProjectStatusService;
import com.dashboard.api.service.server.ServerService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing {@link Project} entities. Implements CRUD operations and
 * business logic related to projects, including validation against servers assigned to projects.
 */
@Service
public class ProjectService extends BaseService<Project, ProjectPresenter, ProjectRegisterInput> {

  private final ProjectRepository projectRepository;
  private final ProjectStatusService projectStatusService;
  private final ServerService serverService;

  /**
   * Constructs a ProjectService with the specified repository and server service.
   *
   * @param projectRepository the repository used to manage projects persistence
   * @param projectStatusService the repository used to manage projects status persistence
   * @param serverService the repository used to manage server persistence
   */
  public ProjectService(
      EntityManagerHelper entityManagerHelper,
      ProjectRepository projectRepository,
      ProjectStatusService projectStatusService,
      ServerService serverService) {
    super(entityManagerHelper);
    this.projectRepository = projectRepository;
    this.projectStatusService = projectStatusService;
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
    if (projectRepository.existsByName(input.name())) {
      throw new EntityExistsException("There's already a project with the specified name!");
    }

    for (Long serverId : input.serverIds()) {
      if (!serverService.existsById(serverId)) {
        throw new EntityNotFoundException(String.format("Server id %d not found", serverId));
      }
    }

    List<Server> serversSelected = emh.referencesByLong(Server.class, input.serverIds());

    if (isAnyServerRegisteredInProject(input.name(), input.serverIds())) {
      throw new EntityExistsException("Server is already registered in project");
    }

    ProjectStatus defaultStatus = projectStatusService.getDefault();
    Project project =
        new Project.Builder()
            .name(input.name())
            .servers(serversSelected)
            .status(defaultStatus)
            .details(input.details())
            .build();

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
    if (input.id().isEmpty()) {
      throw new IllegalArgumentException("id is required!");
    }

    Project project =
        projectRepository
            .findById(input.id().get())
            .orElseThrow(() -> new EntityNotFoundException("Couldn't find specified project"));

    List<Server> serversSelected = emh.referencesByLong(Server.class, input.serverIds());

    ProjectStatus status = emh.reference(ProjectStatus.class, input.statusId());

    project.update(input.name(), serversSelected, status, input.details());

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
