package com.dashboard.api.persistence.jpa.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.api.domain.authority.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
