package com.dashboard.api.service.base;

import com.dashboard.api.domain.entitymanager.EntityManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract base service implementation providing common functionality.
 *
 * @param <T> the output/presenter type
 * @param <X> the detailed view type returned by get(Long id)
 * @param <I> the input type used for register and update
 */
public abstract class BaseService<T, X, I> implements CrudService<T, X, I> {
  protected final EntityManagerHelper emh;

  @Autowired
  public BaseService(EntityManagerHelper entityManagerHelper) {
    this.emh = entityManagerHelper;
  }
}
