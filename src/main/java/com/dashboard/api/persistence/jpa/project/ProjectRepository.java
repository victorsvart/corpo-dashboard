package com.dashboard.api.persistence.jpa.project;

import com.dashboard.api.domain.project.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Project entities.
 *
 * <p>Extends JpaRepository to provide standard CRUD operations, with additional custom queries to
 * find projects by name, check existence by name, and verify server associations.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
  Optional<Project> getByName(String name);

  @Query("SELECT p.id FROM Project p WHERE p.name = :name")
  Optional<Long> findIdByName(String name);

  @Query(
      value =
          """
      SELECT EXISTS (
          SELECT 1 FROM projects WHERE name = :name
      )
      """,
      nativeQuery = true)
  boolean existsByName(String name);

  @Query(
      value =
          """
      SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
      FROM "ProjectServers"
      WHERE project_id = :projectId
      AND server_id IN (:serverIds)
      """,
      nativeQuery = true)
  boolean existsByNameAndServerIds(Long projectId, List<Long> serverIds);
}
