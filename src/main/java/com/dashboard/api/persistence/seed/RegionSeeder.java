package com.dashboard.api.persistence.seed;

import com.dashboard.api.domain.region.Region;
import com.dashboard.api.persistence.jpa.region.RegionRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** Seeder component to initialize default server regions in the database at application startup. */
@Component
@Order(5)
public class RegionSeeder implements CommandLineRunner {
  private final RegionRepository regionRepository;

  public RegionSeeder(RegionRepository regionRepository) {
    this.regionRepository = regionRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    final List<Region> regions =
        List.of(new Region("BRAZIL-01", "SP-01"), new Region("US-01", "WEST-01"));

    for (Region r : regions) {
      if (regionRepository.existsByName(r.getName())) {
        continue;
      }

      regionRepository.save(r);
    }
  }
}
