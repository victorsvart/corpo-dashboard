package com.dashboard.api.domain.project;

import com.dashboard.api.domain.projectstatus.ProjectStatus;
import com.dashboard.api.domain.server.Server;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a project entity that can be associated with multiple servers and has a status. Tracks
 * creation and last update timestamps via Spring Data JPA auditing.
 */
@Entity
@Table(name = "projects")
@EntityListeners(AuditingEntityListener.class)
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "project_servers",
      joinColumns = @JoinColumn(name = "project_id"),
      inverseJoinColumns = @JoinColumn(name = "server_id"))
  private Set<Server> servers;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "status_id", nullable = false)
  private ProjectStatus status;

  @Column(name = "details")
  private String details;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  public Project() {}

  public Project(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Project(String name) {
    this.name = name;
  }

  public Project(String name, List<Server> servers) {
    this.name = name;
    setServers(servers);
  }

  /**
   * Constructor to create a full instance of Project.
   *
   * @param name name of the project.
   * @param servers project's servers
   * @param status project's status
   * @param details project's details
   */
  public Project(String name, List<Server> servers, ProjectStatus status, String details) {
    setName(name);
    setServers(servers);
    setStatus(status);
    setDetails(details);
  }

  /**
   * Fully updated the project entity.
   *
   * @param name name of the project
   * @param servers servers of the project
   * @param status status of the project
   * @param details details of the project
   */
  public void update(String name, List<Server> servers, ProjectStatus status, String details) {
    setName(name);
    setServers(servers);
    setStatus(status);
    setDetails(details);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * Sets the name of the project.
   *
   * @param name the new name
   * @throws IllegalArgumentException if name is empty or blank
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("name can't be empty!");
    }
    this.name = name;
  }

  public Set<Server> getServers() {
    return servers;
  }

  /**
   * Sets the servers associated with the project.
   *
   * @param servers list of server entities
   */
  public void setServers(List<Server> servers) {
    this.servers = new HashSet<>(servers);
  }

  public ProjectStatus getStatus() {
    return status;
  }

  /**
   * Sets the status of the project.
   *
   * @param status the new status
   * @throws IllegalArgumentException if status is null
   */
  public void setStatus(ProjectStatus status) {
    if (status == null) {
      throw new IllegalArgumentException("Status must not be null");
    }
    this.status = status;
  }

  public String getDetails() {
    return details;
  }

  /**
   * Sets the project details if not blank.
   *
   * @param details project description
   */
  public void setDetails(String details) {
    if (details == null || details.isBlank()) {
      return;
    }
    this.details = details;
  }

  /**
   * Returns the name of the project status.
   *
   * @return status name
   */
  public String getStatusName() {
    return status.getName();
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Builds the {@link Project} instance using provided values.
   *
   * @return a fully initialized Server entity
   * @throws IllegalArgumentException if name is null or blank
   */
  public static class Builder {
    private String name;
    private List<Server> servers;
    private ProjectStatus status;
    private String details;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder servers(List<Server> servers) {
      this.servers = servers;
      return this;
    }

    public Builder status(ProjectStatus status) {
      this.status = status;
      return this;
    }

    public Builder details(String details) {
      this.details = details;
      return this;
    }

    public Project build() {
      return new Project(name, servers, status, details);
    }
  }
}
