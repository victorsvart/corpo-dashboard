package com.dashboard.api.persistence.jpa.region;

import com.dashboard.api.domain.region.Region;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository interface for accessing Region entities. */
@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
  boolean existsByName(String name);

  Optional<Region> findByName(String name);
}
