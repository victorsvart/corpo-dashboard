package com.dashboard.api.persistence.jpa.server;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.api.domain.server.Server;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    boolean existsByName(String name);

    Optional<Server> getByName(String name);
}
