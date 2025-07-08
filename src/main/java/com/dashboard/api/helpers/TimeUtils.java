package com.dashboard.api.helpers;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtils {
  public enum TimeCategory {
    SECONDS, MINUTES, HOURS, DAYS
  }

  public static TimeCategory getTimeCategory(long seconds) {
    if (seconds < 60)
      return TimeCategory.SECONDS;
    if (seconds < 3600)
      return TimeCategory.MINUTES;
    if (seconds < 86400)
      return TimeCategory.HOURS;
    return TimeCategory.DAYS;
  }

  public static String formatRelativeTime(LocalDateTime updatedAt, LocalDateTime now) {
    long seconds = Duration.between(updatedAt, now).getSeconds();
    TimeCategory tc = getTimeCategory(seconds);

    switch (tc) {
      case SECONDS -> {
        return seconds + "s";
      }
      case MINUTES -> {
        return (seconds / 60) + "min";
      }
      case HOURS -> {
        return (seconds / 3600) + "h";
      }
      case DAYS -> {
        return (seconds / 86400) + "d";
      }
      default -> throw new IllegalStateException("Unexpected time category");
    }
  }
}
