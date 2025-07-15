package com.dashboard.api.domain.user;

import com.dashboard.api.domain.authority.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a user in the system.
 *
 * <p>Maps to the "app_users" table and stores user information such as username, password, full
 * name, profile picture, and associated authorities. Supports auditing for creation and update
 * timestamps.
 */
@Entity
@Table(name = "app_users")
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  private String name;
  private String lastName;
  private String profilePicture;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_authority",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id"))
  private Set<Authority> authorities;

  protected User() {}

  private User(Builder builder) {
    this.username = builder.username;
    this.password = builder.password;
    this.name = builder.name;
    this.lastName = builder.lastName;
    this.profilePicture = builder.profilePicture;
    this.authorities = builder.authorities;
  }

  /**
   * Builder class for constructing {@link User} instances.
   *
   * <p>Provides a fluent API to set optional and required fields for a User. Mandatory fields such
   * as {@code username} should be validated inside their respective setters. Once all desired
   * fields are configured, call {@link #build()} to create the {@code User} object.
   */
  public static class Builder {
    private String username;
    private String password;
    private String name;
    private String lastName;
    private String profilePicture;
    private Set<Authority> authorities;

    /**
     * Sets the username for the user being built.
     *
     * @param username the username to assign; must not be {@code null} or blank
     * @return the builder instance for chaining
     * @throws IllegalArgumentException if the provided username is {@code null} or blank
     */
    public Builder username(String username) {
      if (username == null || username.isBlank()) {
        throw new IllegalArgumentException("username can't be null or blank");
      }
      this.username = username;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder profilePicture(String profilePicture) {
      this.profilePicture = profilePicture;
      return this;
    }

    public Builder authorities(Set<Authority> authorities) {
      this.authorities = authorities;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }

  public void setDefaultAuthority() {
    this.authorities = Authority.defaultAuthority();
  }

  public void update(String name, String lastName) {
    this.name = name;
    this.lastName = lastName;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  /**
   * Updates the username of this user.
   *
   * @param username the new username to assign; must not be {@code null} or blank
   * @throws IllegalArgumentException if the provided username is {@code null} or blank
   */
  public void setUsername(String username) {
    if (username == null || username.isBlank()) {
      throw new IllegalArgumentException("username can't be null or blank");
    }
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
