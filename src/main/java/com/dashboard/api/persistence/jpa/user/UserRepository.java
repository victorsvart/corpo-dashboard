package com.dashboard.api.persistence.jpa.user;

import com.dashboard.api.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing User entities.
 *
 * <p>Extends JpaRepository to provide CRUD operations on User objects, with additional methods for
 * querying by username and checking existence.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByUsernameAndUsernameNot(String toSearch, String username);
}
