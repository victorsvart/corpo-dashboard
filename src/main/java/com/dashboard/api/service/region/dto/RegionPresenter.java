package com.dashboard.api.service.region.dto;

import com.dashboard.api.domain.region.Region;

/**
 * Data Transfer Object (DTO) for presenting region information.
 *
 * <p>This class provides a simplified representation of a {@link Region} entity, typically used in
 * API responses to expose only necessary region data.
 */
public class RegionPresenter {
  public String name;

  public RegionPresenter(String name) {
    this.name = name;
  }

  public static RegionPresenter from(Region region) {
    return new RegionPresenter(region.getName());
  }
}
