package com.dashboard.api.service.base;

import java.util.List;

import com.dashboard.api.service.base.dto.RegistrationInputBase;

public interface BaseService<T, I extends RegistrationInputBase> {
    List<T> getAll();

    T get(Long id);

    T register(I input);

    String delete(Long id);
}
