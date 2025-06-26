package com.github.aleksikangas.visualvm.integration.options.misc;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public record VisualVmClassPaths(@NotNull String... classPaths) {
  public static final VisualVmClassPaths EMPTY = new VisualVmClassPaths();

  public static VisualVmClassPaths copyOf(@NotNull final VisualVmClassPaths other) {
    return new VisualVmClassPaths(Arrays.copyOf(other.classPaths, other.classPaths.length));
  }

  @Override
  public @NotNull String toString() {
    return String.join(",", Arrays.asList(classPaths));
  }

  public static VisualVmClassPaths ofCommaSeparated(final String commasSeparatedClassPath) {
    return new VisualVmClassPaths(commasSeparatedClassPath.split(","));
  }

  public boolean isEmpty() {
    return classPaths.length == 0;
  }
}
