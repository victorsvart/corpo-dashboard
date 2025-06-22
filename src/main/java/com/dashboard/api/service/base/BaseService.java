package com.dashboard.api.service.base;

import java.util.List;

public interface BaseService<T, X, I> {
  List<T> getAll();

  X get(Long id);

  T register(I input);

  T update(I input);

  String delete(Long id);
}
