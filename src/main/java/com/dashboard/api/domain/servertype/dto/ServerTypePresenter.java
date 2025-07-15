package com.dashboard.api.domain.servertype.dto;

import com.dashboard.api.domain.servertype.ServerType;

/**
 * Data Transfer Object (DTO) for presenting server type information.
 *
 * <p>This class provides a simplified view of the {@link ServerType} entity, typically used for API
 * responses to present only essential server type data.
 */
public class ServerTypePresenter {
  public String name;

  public ServerTypePresenter(String name) {
    this.name = name;
  }

  public static ServerTypePresenter from(ServerType type) {
    return new ServerTypePresenter(type.getName());
  }
}
