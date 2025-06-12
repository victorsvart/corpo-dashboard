package com.dashboard.api.application.controllers.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.api.domain.project.Project;
import com.dashboard.api.service.project.ProjectService;
import com.dashboard.api.service.project.dto.ProjectRegisterInput;

@RestController
@EnableMethodSecurity
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('USER')")
    public List<Project> getAll() {
        return projectService.getAll();
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public Project get(@RequestParam Long id) {
        return projectService.get(id);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('USER')")
    public Project register(@RequestBody ProjectRegisterInput input) {
        return projectService.register(input);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public String delete(@RequestParam Long id) {
        return projectService.delete(id);
    }
}
