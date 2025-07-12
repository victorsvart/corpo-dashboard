package com.dashboard.api.helpers;

/** Utility class for common string operations. */
public class StringUtils {

  /**
   * Capitalizes the first letter of the given word and converts the rest to lowercase.
   *
   * <p>If the input is null or empty, it is returned as is.
   *
   * @param word the input string to capitalize
   * @return the capitalized string, or the original input if null or empty
   */
  public static String capitalizeWord(String word) {
    if (word == null || word.isEmpty()) {
      return word;
    }

    word = word.toLowerCase();
    return Character.toUpperCase(word.charAt(0)) + word.substring(1);
  }
}
