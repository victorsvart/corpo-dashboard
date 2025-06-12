package com.dashboard.api.service.project;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.domain.server.Server;
import com.dashboard.api.persistence.jpa.project.ProjectRepository;
import com.dashboard.api.service.base.BaseService;
import com.dashboard.api.service.project.dto.ProjectRegisterInput;
import com.dashboard.api.service.server.ServerService;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectService implements BaseService<Project, ProjectRegisterInput> {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ServerService serverService;

    private boolean isAnyServerRegisteredInProject(String projectName, List<Long> serverIds) {
        Optional<Long> projectId = projectRepository.findIdByName(projectName);
        if (projectId.isPresent() && projectRepository.existsByNameAndServerIds(projectId.get(), serverIds))
            return true;

        return false;
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Project get(Long id) throws EntityNotFoundException {
        return projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project not found"));
    }

    public Project register(ProjectRegisterInput input) throws EntityExistsException, EntityNotFoundException {
        if (projectRepository.exists(Example.of(new Project(input.name))))
            throw new EntityExistsException("There already a project with the specified name!");

        List<Server> serversSelected = serverService.getMany(input.serverIds);
        if (serversSelected.isEmpty())
            throw new EntityNotFoundException("Server not found");

        if (isAnyServerRegisteredInProject(input.name, input.serverIds))
            throw new EntityExistsException("Server is already registered in project");

        Project project = new Project(input.name, serversSelected);
        return projectRepository.save(project);
    }

    public String delete(Long id) {
        projectRepository.deleteById(id);
        return "Deleted sucessfully";
    }
}
