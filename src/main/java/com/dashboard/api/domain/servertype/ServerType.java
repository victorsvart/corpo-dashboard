package com.dashboard.api.domain.servertype;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a server type within the system.
 *
 * <p>Each server type has a unique name and audit timestamps for creation and updates. This entity
 * is tracked using JPA and includes automatic auditing via {@link AuditingEntityListener}.
 *
 * <p>Intended to distinguish between different environments such as production, development, or
 * staging (DEV, PROD, STAGING).
 */
@Entity
@Table(name = "server_types")
@EntityListeners(AuditingEntityListener.class)
public class ServerType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false, unique = true, length = 50)
  private String name;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  protected ServerType() {}

  public ServerType(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public ServerType(String name) {
    this.name = name;
  }

  public static final String PRODUCTION = "PRODUCTION";
  public static final String STAGING = "STAGING";
  public static final String DEVELOPMENT = "DEVELOPMENT";

  public static final List<String> ALL_TYPES = List.of(DEVELOPMENT, STAGING, PRODUCTION);

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * Sets the server type's name after validating that it is not blank.
   *
   * @param name the name to assign to the server; must not be null or blank
   * @throws IllegalArgumentException if the name is null or blank
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Server type name can't be blank");
    }
    this.name = name;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Builds the {@link ServerType} instance using provided values.
   *
   * @return a fully initialized Server entity
   * @throws IllegalArgumentException if name is null or blank
   */
  public static class Builder {
    private String name;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public ServerType build() {
      return new ServerType(name);
    }
  }
}
