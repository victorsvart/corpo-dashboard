package com.dashboard.api.service.mapper;

import java.util.List;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.domain.server.Server;
import com.dashboard.api.domain.user.User;
import com.dashboard.api.service.project.dto.ProjectRegisterInput;
import com.dashboard.api.service.server.dto.ServerRegisterInput;
import com.dashboard.api.service.user.dto.RegisterRequest;
import com.dashboard.api.service.user.dto.UpdateUserInput;

public class Mapper {
  public static User from(RegisterRequest request) {
    return new User(
        request.username(),
        request.password(),
        request.firstName(),
        request.lastName(),
        null,
        request.authorities());
  }

  public static void fromTo(UpdateUserInput request, User user) {
    user.update(request.firstName(), request.lastName());
  }

  public static Server from(ServerRegisterInput input) {
    return new Server(input.name);
  }

  public static void fromTo(ServerRegisterInput input, Server server) {
    server.update(input.name);
  }

  public static Project from(ProjectRegisterInput input, List<Server> servers) {
    return new Project(input.name, servers);
  }

  public static void fromTo(ProjectRegisterInput input, Project project) {
    String details = input.details != null ? input.details : "";
    project.update(input.name, details);
  }
}
