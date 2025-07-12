package com.dashboard.api.service.mapper;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.domain.server.Server;
import com.dashboard.api.domain.user.User;
import com.dashboard.api.service.project.dto.ProjectRegisterInput;
import com.dashboard.api.service.server.dto.ServerRegisterInput;
import com.dashboard.api.service.user.dto.RegisterRequest;
import com.dashboard.api.service.user.dto.UpdateUserInput;
import java.util.List;

/** Utility class for mapping DTOs to domain entities and updating entities from DTOs. */
public class Mapper {

  /**
   * Creates a User entity from a RegisterRequest DTO.
   *
   * @param request the RegisterRequest containing user registration data
   * @return a new User entity
   */
  public static User from(RegisterRequest request) {
    return new User(
        request.username(),
        request.password(),
        request.firstName(),
        request.lastName(),
        null,
        request.authorities());
  }

  /**
   * Creates a Server entity from a ServerRegisterInput DTO.
   *
   * @param input the ServerRegisterInput DTO containing server registration data
   * @return a new Server entity
   */
  public static Server from(ServerRegisterInput input) {
    return new Server(input.name);
  }

  /**
   * Creates a Project entity from a ProjectRegisterInput DTO and associated servers.
   *
   * @param input the ProjectRegisterInput DTO containing project registration data
   * @param servers the list of Server entities associated with the project
   * @return a new Project entity
   */
  public static Project from(ProjectRegisterInput input, List<Server> servers) {
    return new Project(input.name, servers);
  }

  /**
   * Updates a User entity using data from an UpdateUserInput DTO.
   *
   * @param request the UpdateUserInput containing updated user data
   * @param user the User entity to update
   */
  public static void fromTo(UpdateUserInput request, User user) {
    user.update(request.firstName(), request.lastName());
  }

  /**
   * Updates a Server entity using data from a ServerRegisterInput DTO.
   *
   * @param input the ServerRegisterInput DTO containing updated server data
   * @param server the Server entity to update
   */
  public static void fromTo(ServerRegisterInput input, Server server) {
    server.update(input.name);
  }

  /**
   * Updates a Project entity using data from a ProjectRegisterInput DTO.
   *
   * @param input the ProjectRegisterInput DTO containing updated project data
   * @param project the Project entity to update
   */
  public static void fromTo(ProjectRegisterInput input, Project project) {
    String details = input.details != null ? input.details : "";
    project.update(input.name, details);
  }
}
