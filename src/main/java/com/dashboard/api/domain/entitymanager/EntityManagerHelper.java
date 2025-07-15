package com.dashboard.api.domain.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Helper component to provide convenience methods for JPA {@link EntityManager} operations.
 *
 * <p>Currently supports retrieving entity references (proxies) by class and ID without triggering a
 * database query.
 */
@Component
public class EntityManagerHelper {

  @PersistenceContext private EntityManager entityManager;

  /**
   * Returns a reference (proxy) to the entity of the given class with the specified primary key.
   *
   * <p>This method does not hit the database immediately. Instead, it returns a lazy proxy that
   * will be initialized only when its properties are accessed.
   *
   * @param <T> the entity type
   * @param entityClass the class of the entity to get a reference for
   * @param id the primary key of the entity to reference; must not be null (Long)
   * @return a reference to the entity with the given ID
   * @throws IllegalArgumentException if the ID is null
   */
  public <T> T reference(Class<T> entityClass, Long id) {
    if (id == null) {
      throw new IllegalArgumentException("ID must not be null");
    }
    return entityManager.getReference(entityClass, id);
  }

  /**
   * Returns a reference (proxy) to the entity of the given class with the specified primary key.
   *
   * <p>This method does not hit the database immediately. Instead, it returns a lazy proxy that
   * will be initialized only when its properties are accessed.
   *
   * @param <T> the entity type
   * @param entityClass the class of the entity to get a reference for
   * @param id the primary key of the entity to reference; must not be null (Integer)
   * @return a reference to the entity with the given ID
   * @throws IllegalArgumentException if the ID is null
   */
  public <T> T reference(Class<T> entityClass, Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("ID must not be null");
    }
    return entityManager.getReference(entityClass, id);
  }

  /**
   * Returns a list of entity references (proxies) for the given entity class and list of IDs.
   *
   * <p>This method does not immediately hit the database; each reference is a lazy proxy
   * initialized only when accessed.
   *
   * @param <T> the entity type
   * @param entityClass the class of the entity
   * @param ids a list of Long IDs of the entities
   * @return a list of lazy references to the entities
   * @throws IllegalArgumentException if the ID list is null or contains nulls
   */
  public <T> List<T> referencesByInt(Class<T> entityClass, List<Integer> ids) {
    if (ids == null || ids.contains(null)) {
      throw new IllegalArgumentException("ID list must not be null or contain nulls");
    }
    return ids.stream().map(id -> entityManager.getReference(entityClass, id)).toList();
  }

  /**
   * Returns a list of entity references (proxies) for the given entity class and list of IDs.
   *
   * <p>This method does not immediately hit the database; each reference is a lazy proxy
   * initialized only when accessed.
   *
   * @param <T> the entity type
   * @param entityClass the class of the entity
   * @param ids a list of Long IDs of the entities
   * @return a list of lazy references to the entities
   * @throws IllegalArgumentException if the ID list is null or contains nulls
   */
  public <T> List<T> referencesByLong(Class<T> entityClass, List<Long> ids) {
    if (ids == null || ids.contains(null)) {
      throw new IllegalArgumentException("ID list must not be null or contain nulls");
    }
    return ids.stream().map(id -> entityManager.getReference(entityClass, id)).toList();
  }
}
