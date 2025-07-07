package com.dashboard.api.persistence.jpa.serverStatus;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.api.domain.serverStatus.ProjectStatus;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Integer> {
  boolean existsByName(String name);

  Optional<ProjectStatus> findByName(String name);
}
