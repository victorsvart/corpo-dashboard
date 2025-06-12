package com.dashboard.api.service.project.dto;

import java.util.List;

import com.dashboard.api.service.base.dto.RegistrationInputBase;

public class ProjectRegisterInput implements RegistrationInputBase {
    public String name;
    public List<Long> serverIds;
}
