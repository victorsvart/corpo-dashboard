package com.dashboard.api.service.serverstatus;

import com.dashboard.api.domain.serverstatus.ServerStatus;
import com.dashboard.api.persistence.jpa.serverstatus.ServerStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling operations related to ServerStatus entities. It interacts
 * with the ServerStatusRepository to fetch ServerStatus data.
 */
@Service
public class ServerStatusService {
  private final ServerStatusRepository serverStatusRepository;

  public ServerStatusService(ServerStatusRepository serverStatusRepository) {
    this.serverStatusRepository = serverStatusRepository;
  }

  private int getIdByName(String name) throws EntityNotFoundException {
    Optional<Integer> status = serverStatusRepository.findIdByName(name);

    if (status.isEmpty()) {
      throw new EntityNotFoundException("Specified server status not found");
    }

    return status.get();
  }

  /**
   * Retrieves a {@link ServerStatus} entity by its ID.
   *
   * @param id the ID of the server status to retrieve
   * @return the {@link ServerStatus} entity with the given ID
   * @throws EntityNotFoundException if no server status is found for the given ID
   */
  public ServerStatus getById(int id) throws EntityNotFoundException {
    return serverStatusRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Can't find specified server type"));
  }

  /**
   * Retrieves the ServerStatus entity with the name "ACTIVE".
   *
   * @return the ServerStatus entity ID with the name "ACTIVE"
   * @throws EntityNotFoundException if no ServerStatus with the name "ACTIVE" is found
   */
  public int getActive() throws EntityNotFoundException {
    return getIdByName("ACTIVE");
  }

  /**
   * Retrieves the ServerStatus entity with the name "INACTIVE".
   *
   * @return the ServerStatus entity ID with the name "INACTIVE"
   * @throws EntityNotFoundException if no ServerStatus with the name "INACTIVE" is found
   */
  public int getInactive() throws EntityNotFoundException {
    return getIdByName("INACTIVE");
  }
}
