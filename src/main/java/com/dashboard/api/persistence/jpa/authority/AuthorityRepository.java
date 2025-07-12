package com.dashboard.api.persistence.jpa.authority;

import com.dashboard.api.domain.authority.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Authority entities.
 *
 * <p>Extends JpaRepository to provide CRUD operations on Authority objects, identified by their
 * String authority name.
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
