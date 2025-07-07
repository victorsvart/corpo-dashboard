package com.dashboard.api.persistence.seed;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.dashboard.api.service.server.ServerService;
import com.dashboard.api.service.server.dto.ServerRegisterInput;

import jakarta.persistence.EntityExistsException;

@Component
@Order(5)
public class ServerSeed implements CommandLineRunner {
  private final ServerService serverService;

  public ServerSeed(ServerService serverService) {
    this.serverService = serverService;
  }

  @Override
  public void run(String... args) throws Exception {
    final List<ServerRegisterInput> serversInput = List.of(
        new ServerRegisterInput("SERVER-001"),
        new ServerRegisterInput("SERVER-002"),
        new ServerRegisterInput("SERVER-003"));

    for (ServerRegisterInput serverInput : serversInput) {
      try {
        serverService.register(serverInput);
      } catch (EntityExistsException e) {
        System.out.println("Server " + serverInput.getName() + " already registered.");
        return;
      }
    }
    System.out.println("Seeded servers");
  }
}
