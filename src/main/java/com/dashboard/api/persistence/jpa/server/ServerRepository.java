package com.dashboard.api.persistence.jpa.server;

import com.dashboard.api.domain.server.Server;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Server entities.
 *
 * <p>Extends JpaRepository to provide CRUD operations, along with custom methods to check existence
 * and retrieve servers by name.
 */
@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
  boolean existsByName(String name);

  boolean existsById(@NonNull Long id);

  Optional<Server> getByName(String name);
}
