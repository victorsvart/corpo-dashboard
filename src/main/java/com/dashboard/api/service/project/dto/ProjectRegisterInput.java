package com.dashboard.api.service.project.dto;

import java.util.List;
import java.util.Optional;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.service.base.dto.RegistrationInputBase;

public class ProjectRegisterInput implements RegistrationInputBase<Project> {
    public Optional<Long> id;
    public String name;
    public List<Long> serverIds;

    public Project to(Project project) {
        project.update(name);
        return project;
    }
}
