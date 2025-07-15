package com.dashboard.api.persistence.jpa.servertypes;

import com.dashboard.api.domain.servertype.ServerType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository interface for accessing ServerType entities. */
@Repository
public interface ServerTypeRepository extends JpaRepository<ServerType, Integer> {
  boolean existsByName(String name);

  Optional<ServerType> findByName(String name);
}
