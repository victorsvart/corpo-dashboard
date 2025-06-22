package com.dashboard.api.domain.user;

import java.util.Set;

import com.dashboard.api.domain.authority.Authority;
import com.dashboard.api.service.user.dto.RegisterRequest;
import com.dashboard.api.service.user.dto.UpdateUserInput;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String username;
  private String name;
  private String lastName;
  private String password;
  private String profilePicture;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "UserAuthority", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "authority"))
  private Set<Authority> authorities;

  protected User() {
  }

  public User(Long id, String username, String password, String name, String lastName, String profilePicture,
      Set<Authority> authorities) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.lastName = lastName;
    this.password = password;
    this.profilePicture = profilePicture;
    this.authorities = authorities;
  }

  public User(String username, String password, String name, String lastName, String profilePicture,
      Set<Authority> authorities) {
    this.name = name;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
    this.profilePicture = profilePicture;
  }

  public void setDefaultAuthority() {
    this.setAuthorities(Authority.defaultAuthority());
  }

  public void update(String name, String lastName) {
    this.name = name;
    this.lastName = lastName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    if (username.isEmpty() || username.isBlank())
      throw new IllegalArgumentException("username can't be empty");

    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }
}
