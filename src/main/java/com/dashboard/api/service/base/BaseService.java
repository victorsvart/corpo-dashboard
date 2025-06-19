package com.dashboard.api.service.base;

import java.util.List;

import com.dashboard.api.service.base.dto.RegistrationInputBase;

public interface BaseService<T, X, I extends RegistrationInputBase<T>> {
  List<T> getAll();

  X get(Long id);

  T register(I input);

  T update(I input);

  String delete(Long id);
}
