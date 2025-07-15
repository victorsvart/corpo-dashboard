package com.dashboard.api.service.server.dto;

import java.util.Optional;

/**
 * Data transfer object used to capture input data for registering or updating a server.
 *
 * <p>Contains server properties such as an optional ID, name, region ID, type ID, and status ID.
 * Used typically in service or controller layers for input validation and mapping.
 */
public class ServerRegisterInput {

  /**
   * Optional ID of the server. Present if updating an existing server, empty if creating a new one.
   */
  public Optional<Long> id;

  /** The name of the server. */
  public String name;

  /** The identifier for the region the server belongs to. */
  public int regionId;

  /** The identifier for the type of the server. */
  public int typeId;

  /** The identifier for the status of the server. */
  public int statusId;

  /** Default no-argument constructor. */
  public ServerRegisterInput() {}

  /**
   * Constructs a new {@code ServerRegisterInput} with the specified name, region ID, and type ID.
   *
   * @param name the server name
   * @param regionId the ID of the server's region
   * @param typeId the ID of the server's type
   */
  public ServerRegisterInput(String name, int regionId, int typeId) {
    this.name = name;
    this.regionId = regionId;
    this.typeId = typeId;
  }

  /**
   * Constructs a new {@code ServerRegisterInput} with the specified name, region ID, type ID, and
   * status ID.
   *
   * @param name the server name
   * @param regionId the ID of the server's region
   * @param typeId the ID of the server's type
   * @param statusId the ID of the server's status
   */
  public ServerRegisterInput(String name, int regionId, int typeId, int statusId) {
    this.name = name;
    this.regionId = regionId;
    this.typeId = typeId;
    this.statusId = statusId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
