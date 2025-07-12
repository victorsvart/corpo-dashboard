package com.dashboard.api.service.server.dto;

import com.dashboard.api.domain.server.Server;
import java.util.List;
import java.util.stream.Collectors;

public class ServerPresenter {
  public Long id;
  public String name;
  public boolean active;

  public ServerPresenter(Long id, String name, boolean active) {
    this.id = id;
    this.name = name;
    this.active = active;
  }

  public static ServerPresenter from(Server server) {
    return new ServerPresenter(server.getId(), server.getName(), server.isActive());
  }

  public static List<ServerPresenter> fromMany(List<Server> servers) {
    return servers.stream().map(ServerPresenter::from).collect(Collectors.toList());
  }
}
