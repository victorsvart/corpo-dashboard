package com.dashboard.api.domain.project;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dashboard.api.domain.server.Server;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ProjectServers", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "server_id"))
    private Set<Server> servers;

    public Project() {
    }

    public Project(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Project(String name, List<Server> servers) {
        this.name = name;
        setServers(servers);
    }

    public Project(String name) {
        this.name = name;
    }

    public void update(String name) {
        setName(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty() || name.isBlank())
            throw new IllegalArgumentException("name can't be empty!");

        this.name = name;
    }

    public Set<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = new HashSet<>(servers);
    }
}
