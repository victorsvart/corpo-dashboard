package com.dashboard.api.service.server.dto;

import java.util.Optional;

import com.dashboard.api.domain.server.Server;
import com.dashboard.api.service.base.dto.RegistrationInputBase;

public class ServerRegisterInput implements RegistrationInputBase<Server> {
    public Optional<Long> id;
    public String name;

    public Server to(Server server) {
        server.update(name);
        return server;
    }
}
