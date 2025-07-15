package com.dashboard.api.service.base;

import java.util.List;

/**
 * Generic base service interface defining common CRUD operations.
 *
 * @param <T> the type of the output/presenter object returned by service methods
 * @param <X> the type of the object returned by get(Long id) method (e.g., detailed view)
 * @param <I> the type of the input object used for register and update operations
 */
public interface CrudService<T, X, I> {

  /**
   * Retrieves all instances of the resource.
   *
   * @return a list of all resource representations
   */
  List<T> getAll();

  /**
   * Retrieves a single resource by its ID.
   *
   * @param id the identifier of the resource
   * @return the resource representation
   */
  X get(Long id);

  /**
   * Registers (creates) a new resource.
   *
   * @param input the input data to create the resource
   * @return the created resource representation
   */
  T register(I input);

  /**
   * Updates an existing resource.
   *
   * @param input the input data for updating the resource
   * @return the updated resource representation
   */
  T update(I input);

  /**
   * Deletes a resource by its ID.
   *
   * @param id the identifier of the resource to delete
   * @return a confirmation message or status
   */
  String delete(Long id);
}
