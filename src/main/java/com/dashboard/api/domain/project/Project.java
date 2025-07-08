package com.dashboard.api.domain.project;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dashboard.api.domain.server.Server;
import com.dashboard.api.domain.serverStatus.ProjectStatus;

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
  @JoinTable(name = "ProjectServers", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "server_id"))
  private Set<Server> servers;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "status_id", nullable = false, unique = false)
  private ProjectStatus status;

  @Column(name = "details", nullable = true)
  private String details;

  public Project() {
  }

  public Project(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Project(String name, List<Server> servers) {
    this.name = name;
    setServers(servers);
  }

  public Project(String name) {
    this.name = name;
  }

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

  public void setName(String name) {
    if (name.isEmpty() || name.isBlank())
      throw new IllegalArgumentException("name can't be empty!");

    this.name = name;
  }

  public Set<Server> getServers() {
    return servers;
  }

  public void setServers(List<Server> servers) {
    this.servers = new HashSet<>(servers);
  }

  public ProjectStatus getStatus() {
    return status;
  }

  public void setStatus(ProjectStatus status) {
    if (status == null)
      throw new IllegalArgumentException("Status must not be null");
    this.status = status;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    if (details.isEmpty() || details.isBlank())
      return;

    this.details = details;
  }

  public String getStatusName() {
    return status.getName();
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

}
