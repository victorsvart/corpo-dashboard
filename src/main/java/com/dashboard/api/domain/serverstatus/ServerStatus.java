package com.dashboard.api.domain.serverstatus;

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
 * Represents a server status.
 *
 * <p>Each server will be assigned a status, automatically defaulting to 'ACTIVE'. This entity is
 * tracked using JPA and includes automatic auditing via {@link AuditingEntityListener}.
 *
 * <p>Intended to distinguish between different states such as ACTIVE or INACTIVE.
 */
@Entity
@Table(name = "server_status")
@EntityListeners(AuditingEntityListener.class)
public class ServerStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false, unique = true, length = 20)
  private String name;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  protected ServerStatus() {}

  public ServerStatus(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public ServerStatus(String name) {
    setName(name);
  }

  public static final String ACTIVE = "ACTIVE";
  public static final String INACTIVE = "INACTIVE";

  public static final List<String> ALL_STATUSES = List.of(ACTIVE, INACTIVE);

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * Sets the server status's name after validating that it is not blank.
   *
   * @param name the name to assign to the server; must not be null or blank
   * @throws IllegalArgumentException if the name is null or blank
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Server status name can't be blank");
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
   * Builds the {@link ServerStatus} instance using provided values.
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

    public ServerStatus build() {
      return new ServerStatus(name);
    }
  }
}
