package com.dashboard.api.helpers;

import java.time.Duration;
import java.time.LocalDateTime;

/** Utility class for time-related operations. */
public class TimeUtils {

  /** Enum representing categories of time durations. */
  public enum TimeCategory {
    SECONDS,
    MINUTES,
    HOURS,
    DAYS
  }

  /**
   * Determines the appropriate time category for a duration given in seconds.
   *
   * @param seconds the duration in seconds
   * @return the time category corresponding to the duration
   */
  public static TimeCategory getTimeCategory(long seconds) {
    if (seconds < 60) {
      return TimeCategory.SECONDS;
    }
    if (seconds < 3600) {
      return TimeCategory.MINUTES;
    }
    if (seconds < 86400) {
      return TimeCategory.HOURS;
    }
    return TimeCategory.DAYS;
  }

  /**
   * Formats the relative time difference between two LocalDateTime instances as a concise string.
   *
   * <p>The output uses a shorthand notation: seconds (s), minutes (min), hours (h), or days (d).
   *
   * @param updatedAt the earlier LocalDateTime
   * @param now the later LocalDateTime
   * @return a formatted string representing the time difference (e.g., "45s", "12min", "3h", "2d")
   * @throws IllegalStateException if an unexpected time category occurs
   */
  public static String formatRelativeTime(LocalDateTime updatedAt, LocalDateTime now) {
    long seconds = Duration.between(updatedAt, now).getSeconds();
    TimeCategory tc = getTimeCategory(seconds);

    return switch (tc) {
      case SECONDS -> seconds + "s";
      case MINUTES -> (seconds / 60) + "min";
      case HOURS -> (seconds / 3600) + "h";
      case DAYS -> (seconds / 86400) + "d";
      default -> throw new IllegalStateException("Unexpected time category");
    };
  }
}
