package com.dashboard.api.domain.serverStatus;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;

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
