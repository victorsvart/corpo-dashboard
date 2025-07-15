package com.dashboard.api.service.project.dto;

import java.util.List;
import java.util.Optional;

/** Data transfer object for registering or updating a project. Contains project details. */
public record ProjectRegisterInput(
    Optional<Long> id, String name, List<Long> serverIds, int statusId, String details) {}
