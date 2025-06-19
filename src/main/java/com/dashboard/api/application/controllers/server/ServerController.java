package com.dashboard.api.application.controllers.server;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.api.domain.server.Server;
import com.dashboard.api.service.server.ServerService;
import com.dashboard.api.service.server.dto.ServerPresenter;
import com.dashboard.api.service.server.dto.ServerRegisterInput;

@RestController
@EnableMethodSecurity
@RequestMapping("/server")
public class ServerController {

    private ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('USER')")
    public List<Server> getAll() {
        return serverService.getAll();
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public ServerPresenter get(@RequestParam Long id) {
        return serverService.get(id);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('USER')")
    public Server register(@RequestBody ServerRegisterInput input) {
        return serverService.register(input);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public Server update(@RequestBody ServerRegisterInput input) {
        return serverService.update(input);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public String delete(@RequestParam Long id) {
        return serverService.delete(id);
    }
}
