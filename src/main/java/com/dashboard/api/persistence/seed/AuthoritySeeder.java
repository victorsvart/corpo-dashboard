package com.dashboard.api.persistence.seed;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dashboard.api.domain.authority.Authority;
import com.dashboard.api.persistence.jpa.authority.AuthorityRepository;

@Component
@Order(1)
public class AuthoritySeeder implements CommandLineRunner {

  private final AuthorityRepository authorityRepository;

  public AuthoritySeeder(AuthorityRepository authorityRepository) {
    this.authorityRepository = authorityRepository;
  }

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    Set<Authority> defaultAuthorities = Set.of(
        new Authority("ROLE_USER"),
        new Authority("ROLE_ADMIN"));

    for (Authority authority : defaultAuthorities) {
      if (!authorityRepository.existsById(authority.getAuthority())) {
        authorityRepository.save(authority);
      }
    }
  }
}
