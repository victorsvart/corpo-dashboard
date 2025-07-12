package com.dashboard.api.domain.server;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a server instance within the system.
 *
 * <p>Each server has a unique name, an active status, and audit timestamps for creation and
 * updates. This entity is tracked using JPA and includes automatic auditing via {@link
 * AuditingEntityListener}.
 *
 * <p>Intended to distinguish between different environments such as production, development, or
 * homologation (DEV, PROD, HML).
 */
@Entity
@Table(name = "Servers")
@EntityListeners(AuditingEntityListener.class)
public class Server {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  private boolean active = true;
  // TODO: add status and region
  // private String status;
  // private String region;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  // TODO: add a 'type' field like PROD, DEV, HML (update seed too)

  protected Server() {}

  public Server(String name) {
    setName(name);
  }

  public void update(String name) {
    setName(name);
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
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Server name can't be blank");
    }
    this.name = name;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus()
}
