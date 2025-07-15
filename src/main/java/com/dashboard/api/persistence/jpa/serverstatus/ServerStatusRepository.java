package com.dashboard.api.persistence.jpa.serverstatus;

import com.dashboard.api.domain.serverstatus.ServerStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** Repository interface for accessing ServerStatus entities. */
@Repository
public interface ServerStatusRepository extends JpaRepository<ServerStatus, Integer> {
  boolean existsByName(String name);

  Optional<ServerStatus> findByName(String name);

  @Query("SELECT s.id FROM ServerStatus s WHERE s.name = :name")
  Optional<Integer> findIdByName(@Param("name") String name);
}
