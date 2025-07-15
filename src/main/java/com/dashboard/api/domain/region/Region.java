package com.dashboard.api.domain.region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a region within the system.
 *
 * <p>Each region has a unique name and audit timestamps for creation and updates. This entity is
 * tracked using JPA and includes automatic auditing via {@link AuditingEntityListener}.
 */
@Entity
@Table(name = "regions")
@EntityListeners(AuditingEntityListener.class)
public class Region {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false, unique = true, length = 100)
  private String name;

  @Column(nullable = false, unique = true, length = 100)
  private String code;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  protected Region() {}

  /**
   * Constructor to create an existing region entity.
   *
   * @param id entity id
   * @param name region name
   * @param code region code
   */
  public Region(Integer id, String name, String code) {
    this.id = id;
    this.name = name;
    this.code = code;
  }

  public Region(String name, String code) {
    this.name = name;
    this.code = code;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * Sets the region's name after validating that it is not blank.
   *
   * @param name the name to assign to the server; must not be null or blank
   * @throws IllegalArgumentException if the name is null or blank
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Region name can't be black");
    }
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  /**
   * Sets the region's name after validating that it is not blank.
   *
   * @param code the name to assign to the server; must not be null or blank
   * @throws IllegalArgumentException if the name is null or blank
   */
  public void setCode(String code) {
    if (code == null || code.isBlank()) {
      throw new IllegalArgumentException("Region code can't be black");
    }
    this.code = code;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Builder class for flexible construction of {@link Region} instances.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * Region region = new Region.Builder().name("BRAZIL-01").code("SP-01").build();
   * }</pre>
   */
  public static class Builder {
    private String name;
    private String code;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder code(String code) {
      this.code = code;
      return this;
    }

    public Region build() {
      return new Region(name, code);
    }
  }
}
