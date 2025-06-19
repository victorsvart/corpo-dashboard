package com.dashboard.api.service.server;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dashboard.api.domain.server.Server;
import com.dashboard.api.persistence.jpa.server.ServerRepository;
import com.dashboard.api.service.base.BaseService;
import com.dashboard.api.service.server.dto.ServerPresenter;
import com.dashboard.api.service.server.dto.ServerRegisterInput;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ServerService implements BaseService<Server, ServerPresenter, ServerRegisterInput> {

  private ServerRepository serverRepository;

  public ServerService(ServerRepository serverRepository) {
    this.serverRepository = serverRepository;
  }

  public List<Server> getAll() {
    return serverRepository.findAll();
  }

  public ServerPresenter get(Long id) throws EntityNotFoundException {
    Server server = serverRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Server not found"));

    return ServerPresenter.from(server);
  }

  public List<Server> getMany(List<Long> ids) {
    return serverRepository.findAllById(ids);
  }

  public Server register(ServerRegisterInput input) throws EntityExistsException {
    Server server = new Server(input.name);
    if (serverRepository.existsByName(input.name))
      throw new EntityExistsException("Server is already registered");

    return serverRepository.save(server);
  }

  public Server update(ServerRegisterInput input) throws IllegalArgumentException {
    if (input.id.isEmpty())
      throw new IllegalArgumentException("id is required!");

    Server server = serverRepository.findById(input.id.get())
        .orElseThrow(() -> new EntityNotFoundException("Can't find especified server."));

    server = input.to(server);
    return serverRepository.save(server);
  }

  public String delete(Long id) throws IllegalArgumentException {
    serverRepository.deleteById(id);
    return "Deleted sucessfully";
  }
}
