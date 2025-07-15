package com.dashboard.api.service.servertype;

import com.dashboard.api.domain.servertype.ServerType;
import com.dashboard.api.persistence.jpa.servertypes.ServerTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service for managing and retrieving {@link ServerType} entities.
 *
 * <p>Provides methods to fetch server types by ID or name, as well as convenience methods for
 * common predefined types such as production, staging, and development.
 */
@Service
public class ServerTypeService {

  private final ServerTypeRepository serverTypeRepository;

  /**
   * Constructs a new {@code ServerTypeService} with the given repository.
   *
   * @param serverTypeRepository repository for accessing {@link ServerType} entities
   */
  public ServerTypeService(ServerTypeRepository serverTypeRepository) {
    this.serverTypeRepository = serverTypeRepository;
  }

  /**
   * Retrieves a {@link ServerType} by its name.
   *
   * @param name the name of the server type to retrieve
   * @return the {@code ServerType} matching the specified name
   * @throws EntityNotFoundException if no server type with the given name exists
   */
  private ServerType getByName(String name) throws EntityNotFoundException {
    Optional<ServerType> status = serverTypeRepository.findByName(name);

    if (status.isEmpty()) {
      throw new EntityNotFoundException("Specified server status not found");
    }

    return status.get();
  }

  /**
   * Retrieves a {@link ServerType} by its ID.
   *
   * @param id the ID of the server type to retrieve
   * @return the {@code ServerType} with the given ID
   * @throws EntityNotFoundException if no server type with the given ID exists
   */
  public ServerType getById(int id) throws EntityNotFoundException {
    return serverTypeRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Can't find specified server type"));
  }

  /**
   * Retrieves the {@link ServerType} representing the production environment.
   *
   * @return the production server type
   * @throws EntityNotFoundException if the production server type is not found
   */
  public ServerType getProduction() {
    return getByName(ServerType.PRODUCTION);
  }

  /**
   * Retrieves the {@link ServerType} representing the staging environment.
   *
   * @return the staging server type
   * @throws EntityNotFoundException if the staging server type is not found
   */
  public ServerType getStaging() {
    return getByName(ServerType.STAGING);
  }

  /**
   * Retrieves the {@link ServerType} representing the development environment.
   *
   * @return the development server type
   * @throws EntityNotFoundException if the development server type is not found
   */
  public ServerType getDevelopment() {
    return getByName(ServerType.DEVELOPMENT);
  }
}
