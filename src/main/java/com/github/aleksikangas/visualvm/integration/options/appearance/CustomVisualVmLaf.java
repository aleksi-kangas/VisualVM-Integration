package com.github.aleksikangas.visualvm.integration.options.appearance;

import org.jetbrains.annotations.NotNull;

public record CustomVisualVmLaf(@NotNull String className) implements VisualVmLaf {
  @Override
  public String value() {
    return className;
  }

  @Override
  public @NotNull String toString() {
    return value();
  }
}
