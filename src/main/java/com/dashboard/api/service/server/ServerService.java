package com.dashboard.api.service.server;

import com.dashboard.api.domain.entitymanager.EntityManagerHelper;
import com.dashboard.api.domain.region.Region;
import com.dashboard.api.domain.server.Server;
import com.dashboard.api.domain.serverstatus.ServerStatus;
import com.dashboard.api.domain.servertype.ServerType;
import com.dashboard.api.persistence.jpa.server.ServerRepository;
import com.dashboard.api.service.base.BaseService;
import com.dashboard.api.service.server.dto.ServerPresenter;
import com.dashboard.api.service.server.dto.ServerRegisterInput;
import com.dashboard.api.service.serverstatus.ServerStatusService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service class to manage Server entities. Implements CRUD operations and business logic related to
 * servers.
 */
@Service
public class ServerService extends BaseService<Server, ServerPresenter, ServerRegisterInput> {

  private final ServerRepository serverRepository;
  private final ServerStatusService serverStatusService;

  /**
   * Constructs the ServerService with required repositories and services.
   *
   * @param serverRepository the repository for Server entities
   * @param serverStatusService the service for Server status management
   */
  public ServerService(
      EntityManagerHelper entityManagerHelper,
      ServerRepository serverRepository,
      ServerStatusService serverStatusService) {
    super(entityManagerHelper);
    this.serverRepository = serverRepository;
    this.serverStatusService = serverStatusService;
  }

  /**
   * Retrieves all Server entities.
   *
   * @return a list of all servers
   */
  public List<Server> getAll() {
    return serverRepository.findAll();
  }

  /**
   * Retrieves a ServerPresenter by its ID.
   *
   * @param id the id of the server to retrieve
   * @return the ServerPresenter representation of the server
   * @throws EntityNotFoundException if no server with the specified ID exists
   */
  public ServerPresenter get(Long id) throws EntityNotFoundException {
    Server server =
        serverRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Server not found"));
    return ServerPresenter.from(server);
  }

  /**
   * Retrieves a list of Server entities by their IDs.
   *
   * @param ids list of server IDs to fetch
   * @return list of Server entities matching the IDs
   */
  public List<Server> getMany(List<Long> ids) {
    return serverRepository.findAllById(ids);
  }

  /**
   * Registers a new Server entity.
   *
   * @param input the server registration input DTO
   * @return the saved Server entity
   * @throws EntityExistsException if a server with the same name already exists
   * @throws EntityNotFoundException if the specified region does not exist
   */
  public Server register(ServerRegisterInput input)
      throws EntityExistsException, EntityNotFoundException {
    if (serverRepository.existsByName(input.name)) {
      throw new EntityExistsException("Server is already registered");
    }

    ServerType type = emh.reference(ServerType.class, input.typeId);
    Region region = emh.reference(Region.class, input.regionId);
    ServerStatus status = emh.reference(ServerStatus.class, serverStatusService.getActive());

    Server server =
        new Server.Builder().name(input.name).status(status).type(type).region(region).build();

    return serverRepository.save(server);
  }

  /**
   * Updates an existing Server entity.
   *
   * @param input the server registration input DTO containing updated data
   * @return the updated Server entity
   * @throws IllegalArgumentException if the input ID is missing
   * @throws EntityNotFoundException if the server to update cannot be found
   */
  public Server update(ServerRegisterInput input) throws IllegalArgumentException {
    if (input.id.isEmpty()) {
      throw new IllegalArgumentException("id is required!");
    }

    Server server =
        serverRepository
            .findById(input.id.get())
            .orElseThrow(() -> new EntityNotFoundException("Can't find specified server."));

    ServerStatus status = emh.reference(ServerStatus.class, input.statusId);
    ServerType type = emh.reference(ServerType.class, input.typeId);
    Region region = emh.reference(Region.class, input.regionId);

    server.update(input.name, status, type, region);
    return serverRepository.save(server);
  }

  /**
   * Deletes a Server entity by its ID.
   *
   * @param id the ID of the server to delete
   * @return a confirmation message
   */
  public String delete(Long id) throws IllegalArgumentException {
    serverRepository.deleteById(id);
    return "Deleted successfully";
  }

  /**
   * Deactivates a Server by setting its active status to false.
   *
   * @param id the ID of the server to deactivate
   * @return the ServerPresenter representation of the deactivated server
   * @throws EntityNotFoundException if the server cannot be found
   */
  public ServerPresenter deactivateServer(Long id) {
    Server server =
        serverRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Can't find specified server."));

    server.deactivate();
    serverRepository.save(server);

    return ServerPresenter.from(server);
  }

  public boolean existsById(Long id) {
    return serverRepository.existsById(id);
  }
}
