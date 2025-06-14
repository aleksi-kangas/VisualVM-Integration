package com.github.aleksikangas.visualvm.integration.options;

import org.jetbrains.annotations.NotNull;

public final class VisualVmSourceUtils {
  /**
   * Encodes the given value by replacing:
   * <ul>
   *   <li>spaces with '%20'</li>
   *   <li>single quotes with '%27'</li>
   *   <item>double quotes with '%22'</item>
   * </ul>
   * @param value to encode
   * @return encoded value
   */
  public static String encodeValue(@NotNull final String value) {
    return value.replace(" ", "%20")
                .replace("'", "%27")
                .replace("\"", "%22");
  }
}
