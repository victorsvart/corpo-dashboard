package com.dashboard.api.service.region;

import com.dashboard.api.domain.region.Region;
import com.dashboard.api.persistence.jpa.region.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling business logic related to {@link Region} entities.
 * Provides methods to retrieve regions by their ID or name.
 */
@Service
public class RegionService {
  private final RegionRepository regionRepository;

  public RegionService(RegionRepository regionRepository) {
    this.regionRepository = regionRepository;
  }

  /**
   * Retrieves a {@link Region} entity by its unique identifier.
   *
   * @param id the unique identifier of the region
   * @return the {@code Region} entity with the specified ID
   * @throws EntityNotFoundException if no region with the given ID is found
   */
  public Region getById(int id) throws EntityNotFoundException {
    Optional<Region> status = regionRepository.findById(id);

    if (status.isEmpty()) {
      throw new EntityNotFoundException("Specified region not found");
    }

    return status.get();
  }

  /**
   * Retrieves a {@link Region} entity by its unique name.
   *
   * @param name the name of the region
   * @return the {@code Region} entity with the specified name
   * @throws EntityNotFoundException if no region with the given name is found
   */
  public Region getByName(String name) throws EntityNotFoundException {
    Optional<Region> status = regionRepository.findByName(name);

    if (status.isEmpty()) {
      throw new EntityNotFoundException("Specified region not found");
    }

    return status.get();
  }
}
