package com.github.aleksikangas.visualvm.integration.options.appearance;

import org.jetbrains.annotations.NotNull;

public sealed interface VisualVmLaf permits DefaultVisualVmLaf, CustomVisualVmLaf {
  /**
   * @return the LAF value
   */
  String value();

  static @NotNull String value(@NotNull final VisualVmLaf visualVmLaf) {
    return switch (visualVmLaf) {
      case DefaultVisualVmLaf defaultVisualVmLaf -> defaultVisualVmLaf.value();
      case CustomVisualVmLaf customVisualVmLaf -> customVisualVmLaf.value();
    };
  }
}
