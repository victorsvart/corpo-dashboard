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
import java.time.LocalDateTime;
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
@Table(name = "Projects")
@EntityListeners(AuditingEntityListener.class)
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "ProjectServers",
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
  private LocalDateTime createdAt = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  /** Default constructor for JPA. */
  public Project() {}

  /**
   * Constructs a project with a given ID and name.
   *
   * @param id the project ID
   * @param name the project name
   */
  public Project(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Constructs a project with a name and list of servers.
   *
   * @param name the project name
   * @param servers the associated servers
   */
  public Project(String name, List<Server> servers) {
    this.name = name;
    setServers(servers);
  }

  /**
   * Constructs a project with a given name.
   *
   * @param name the project name
   */
  public Project(String name) {
    this.name = name;
  }

  /**
   * Updates the name and details of the project.
   *
   * @param name the new name
   * @param details the new details
   */
  public void update(String name, String details) {
    setName(name);
    setDetails(details);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
