package com.dashboard.api.service.server.dto;

import com.dashboard.api.domain.server.Server;
import com.dashboard.api.domain.servertype.dto.ServerTypePresenter;
import com.dashboard.api.service.region.dto.RegionPresenter;
import com.dashboard.api.service.serverstatus.dto.ServerStatusPresenter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) for presenting server information in API responses.
 *
 * <p>This class transforms a {@link Server} entity into a simplified format for frontend
 * consumption, including details such as the server's type, status, region, and basic metadata.
 */
public class ServerPresenter {
  public Long id;
  public String name;
  public boolean active;
  public ServerTypePresenter type;
  public ServerStatusPresenter status;
  public RegionPresenter region;

  /**
   * Constructs a {@code ServerPresenter} with all required fields.
   *
   * @param id the unique identifier of the server
   * @param name the server's name
   * @param active whether the server is currently active
   * @param type the server's type as a {@link ServerTypePresenter}
   * @param status the server's status as a {@link ServerStatusPresenter}
   * @param region the region where the server is located as a {@link RegionPresenter}
   */
  public ServerPresenter(
      Long id,
      String name,
      boolean active,
      ServerTypePresenter type,
      ServerStatusPresenter status,
      RegionPresenter region) {
    this.id = id;
    this.name = name;
    this.active = active;
    this.type = type;
    this.status = status;
    this.region = region;
  }

  /**
   * Maps a {@link Server} entity to a {@code ServerPresenter}.
   *
   * @param server the server entity to transform
   * @return a {@code ServerPresenter} representing the server
   */
  public static ServerPresenter from(Server server) {
    return new ServerPresenter(
        server.getId(),
        server.getName(),
        server.isActive(),
        ServerTypePresenter.from(server.getServerType()),
        ServerStatusPresenter.from(server.getStatus()),
        RegionPresenter.from(server.getRegion()));
  }

  /**
   * Maps a list of {@link Server} entities to a list of {@code ServerPresenter} DTOs.
   *
   * @param servers the list of server entities to transform
   * @return a list of {@code ServerPresenter} objects
   */
  public static List<ServerPresenter> fromMany(List<Server> servers) {
    return servers.stream().map(ServerPresenter::from).collect(Collectors.toList());
  }
}
