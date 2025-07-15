package com.dashboard.api.domain.server;

import com.dashboard.api.domain.region.Region;
import com.dashboard.api.domain.serverstatus.ServerStatus;
import com.dashboard.api.domain.servertype.ServerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a server instance within the system.
 *
 * <p>Each server has a unique name, a status (e.g., active, inactive), a type (e.g., DEV, PROD,
 * HML), and is assigned to a region. This entity is tracked using JPA and includes automatic
 * auditing via {@link AuditingEntityListener}, providing timestamps for creation and last update.
 *
 * <p>Use {@link Server#create(String, ServerStatus, ServerType, Region)} or the {@link
 * Server.Builder} class to construct a new Server instance. The constructor is protected to ensure
 * consistency and validation.
 */
@Entity
@Table(name = "servers")
@EntityListeners(AuditingEntityListener.class)
public class Server {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  private boolean active = true;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "status_id", nullable = false)
  private ServerStatus status;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "region_id", nullable = false)
  private Region region;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "type_id", nullable = false)
  private ServerType serverType;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  /** JPA-only constructor. Do not use directly. */
  protected Server() {}

  /**
   * Constructs a new Server instance.
   *
   * <p>Use the {@link #create} factory method or {@link Builder} for safe instantiation.
   *
   * @param name the server's unique name (must not be blank)
   * @param status the server's operational status
   * @param type the server's classification (e.g., DEV, PROD)
   * @param region the region the server is assigned to
   * @throws IllegalArgumentException if the name is null or blank
   */
  private Server(String name, ServerStatus status, ServerType type, Region region) {
    setName(name);
    this.status = status;
    this.serverType = type;
    this.region = region;
  }

  /**
   * Updates the server's main properties.
   *
   * @param name new name (must not be blank)
   * @param status new status
   * @param type new server type
   * @param region new region
   */
  public void update(String name, ServerStatus status, ServerType type, Region region) {
    setName(name);
    this.status = status;
    this.serverType = type;
    this.region = region;
  }

  /** Marks the server as inactive. */
  public void deactivate() {
    this.active = false;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * Sets the server's name after validating that it is not blank.
   *
   * @param name the name to assign to the server; must not be null or blank
   * @throws IllegalArgumentException if the name is null or blank
   */
  private void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Server name can't be blank");
    }
    this.name = name;
  }

  public boolean isActive() {
    return active;
  }

  public ServerStatus getStatus() {
    return status;
  }

  public Region getRegion() {
    return region;
  }

  public ServerType getServerType() {
    return serverType;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Builder class for flexible construction of {@link Server} instances.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * Server server = new Server.Builder().name("web-prod-01").status(activeStatus).type(prodType)
   *     .region(saoPauloRegion).build();
   * }</pre>
   */
  public static class Builder {
    private String name;
    private ServerStatus status;
    private ServerType type;
    private Region region;

    /**
     * Sets the server's name.
     *
     * @param name the unique name of the server
     * @return this builder instance
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the server's status.
     *
     * @param status the operational status
     * @return this builder instance
     */
    public Builder status(ServerStatus status) {
      this.status = status;
      return this;
    }

    /**
     * Sets the server type (e.g., DEV, PROD).
     *
     * @param type the type classification
     * @return this builder instance
     */
    public Builder type(ServerType type) {
      this.type = type;
      return this;
    }

    /**
     * Sets the region the server belongs to.
     *
     * @param region the region object
     * @return this builder instance
     */
    public Builder region(Region region) {
      this.region = region;
      return this;
    }

    /**
     * Builds the {@link Server} instance using provided values.
     *
     * @return a fully initialized Server entity
     * @throws IllegalArgumentException if name is null or blank
     */
    public Server build() {
      return new Server(name, status, type, region);
    }
  }
}
