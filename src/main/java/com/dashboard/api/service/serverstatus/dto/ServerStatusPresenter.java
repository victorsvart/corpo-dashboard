package com.dashboard.api.service.serverstatus.dto;

import com.dashboard.api.domain.serverstatus.ServerStatus;

/**
 * Data Transfer Object (DTO) for presenting server status information.
 *
 * <p>This class provides a simplified representation of a {@link ServerStatus} entity, typically
 * used in API responses to expose only necessary fields.
 */
public class ServerStatusPresenter {
  public String name;

  public ServerStatusPresenter(String name) {
    this.name = name;
  }

  public static ServerStatusPresenter from(ServerStatus status) {
    return new ServerStatusPresenter(status.getName());
  }
}
