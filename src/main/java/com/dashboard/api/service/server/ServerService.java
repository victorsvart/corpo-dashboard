package com.dashboard.api.service.server;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.api.domain.server.Server;
import com.dashboard.api.persistence.jpa.server.ServerRepository;
import com.dashboard.api.service.base.BaseService;
import com.dashboard.api.service.server.dto.ServerRegisterInput;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ServerService implements BaseService<Server, ServerRegisterInput> {

    @Autowired
    private ServerRepository serverRepository;

    public List<Server> getAll() {
        return serverRepository.findAll();
    }

    public Server get(Long id) throws EntityNotFoundException {
        return serverRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Server not found"));
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

    public String delete(Long id) throws IllegalArgumentException {
        serverRepository.deleteById(id);
        return "Deleted sucessfully";
    }
}
