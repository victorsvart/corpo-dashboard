package com.dashboard.api.helpers;

public class StringUtils {
  public static String capitalizeWord(String word) {
    if (word == null || word.isEmpty()) {
      return word;
    }

    word = word.toLowerCase();
    return Character.toUpperCase(word.charAt(0)) + word.substring(1);
  }
}
