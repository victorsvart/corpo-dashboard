package com.dashboard.api.domain.projectstatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Represents a status that a project can have.
 *
 * <p>This entity maps to the "ProjectStatuses" table and includes fields for the unique identifier,
 * the status name, and timestamps for creation and last update.
 */
@Entity
@Table(name = "ProjectStatuses")
public class ProjectStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final int id;

  @Column(nullable = false, unique = true)
  private final String name;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  protected ProjectStatus() {
    this.id = 0;
    this.name = null;
  }

  public ProjectStatus(String name) {
    this.id = 0;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
