package com.dashboard.api.persistence.jpa.projectstatus;

import com.dashboard.api.domain.projectstatus.ProjectStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing ProjectStatus entities.
 *
 * <p>Extends JpaRepository to provide CRUD operations on ProjectStatus objects, identified by their
 * Integer ID. Also includes methods to check existence and find ProjectStatus by name.
 */
@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Integer> {
  boolean existsByName(String name);

  Optional<ProjectStatus> findByName(String name);
}
