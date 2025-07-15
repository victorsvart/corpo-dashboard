package com.dashboard.api.domain.authority;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Set;

/**
 * Represents a security authority (role) granted to a user.
 *
 * <p>This entity corresponds to the "Authorities" table and stores roles like "ROLE_USER".
 */
@Entity
@Table(name = "authorities")
public class Authority {
  @Id private String authority;

  protected Authority() {}

  public Authority(String authority) {
    this.authority = authority;
  }

  public static Set<Authority> defaultAuthority() {
    return Set.of(new Authority("ROLE_USER"));
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
