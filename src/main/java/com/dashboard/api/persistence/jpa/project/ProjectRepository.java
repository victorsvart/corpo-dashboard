package com.dashboard.api.persistence.jpa.project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dashboard.api.domain.project.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
  Optional<Project> getByName(String name);

  @Query("SELECT p.id FROM Project p WHERE p.name = :name")
  Optional<Long> findIdByName(String name);

  @Query(value = """
      SELECT EXISTS (
          SELECT 1 FROM projects WHERE name = :name
      )
      """, nativeQuery = true)
  boolean existsByName(String name);

  @Query(value = """
      SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
      FROM project_servers
      WHERE project_id = :projectId
      AND server_id IN (:serverIds)
      """, nativeQuery = true)
  boolean existsByNameAndServerIds(Long projectId, List<Long> serverIds);
}
