package com.dashboard.api.domain.projectstatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Represents a status that a project can have.
 *
 * <p>This entity maps to the "project_statuses" table and includes fields for the unique
 * identifier, the status name, and timestamps for creation and last update.
 */
@Entity
@Table(name = "project_statuses")
public class ProjectStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false, unique = true)
  private String name;

  private boolean isDefault;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  protected ProjectStatus() {}

  public ProjectStatus(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isDefault() {
    return isDefault;
  }

  public void setAsDefault() {
    isDefault = true;
  }

  public void unsetDefault() {
    isDefault = false;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Builds the {@link ProjectStatus} instance using provided values.
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

    public ProjectStatus build() {
      return new ProjectStatus(name);
    }
  }
}
