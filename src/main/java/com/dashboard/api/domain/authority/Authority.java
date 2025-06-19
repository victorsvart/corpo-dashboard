package com.dashboard.api.domain.authority;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Authorities")
public class Authority {
  @Id
  private String authority;

  protected Authority() {
  }

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
