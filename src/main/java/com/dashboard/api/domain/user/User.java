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
 * <p>Maps to the "Users" table and stores user information such as username, password, full name,
 * profile picture, and associated authorities (roles). Supports auditing for creation and update
 * timestamps.
 */
@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String username;
  private String name;
  private String lastName;
  private String password;
  private String profilePicture;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  /**
   * Authorities (roles/permissions) assigned to the user. Eagerly fetched to ensure roles are
   * available immediately.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "UserAuthority",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority"))
  private Set<Authority> authorities;

  protected User() {}

  /**
   * Constructs a user with all attributes including ID.
   *
   * @param id user ID
   * @param username unique username
   * @param password hashed password
   * @param name first name
   * @param lastName last name
   * @param profilePicture profile picture URL/path
   * @param authorities set of authorities assigned to the user
   */
  public User(
      Long id,
      String username,
      String password,
      String name,
      String lastName,
      String profilePicture,
      Set<Authority> authorities) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.lastName = lastName;
    this.password = password;
    this.profilePicture = profilePicture;
    this.authorities = authorities;
  }

  /**
   * Constructs a user without an ID, for new users.
   *
   * @param username unique username
   * @param password hashed password
   * @param name first name
   * @param lastName last name
   * @param profilePicture profile picture URL/path
   * @param authorities set of authorities assigned to the user
   */
  public User(
      String username,
      String password,
      String name,
      String lastName,
      String profilePicture,
      Set<Authority> authorities) {
    this.name = name;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
    this.profilePicture = profilePicture;
  }

  /** Sets the user's authorities to the system default authority. */
  public void setDefaultAuthority() {
    this.setAuthorities(Authority.defaultAuthority());
  }

  /**
   * Updates the user's first and last name.
   *
   * @param name new first name
   * @param lastName new last name
   */
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

  /**
   * Sets the username for the user.
   *
   * @param username new username, must not be null, empty or blank
   * @throws IllegalArgumentException if username is empty or blank
   */
  public void setUsername(String username) {
    if (username.isEmpty() || username.isBlank()) {
      throw new IllegalArgumentException("username can't be empty");
    }

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
