package com.dashboard.api.service.server.dto;

import com.dashboard.api.domain.server.Server;

public class ServerPresenter {
  public Long id;
  public String name;

  public ServerPresenter(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static ServerPresenter from(Server server) {
    return new ServerPresenter(server.getId(), server.getName());
  }
}
