package com.github.aleksikangas.visualvm.integration.options.misc;

import java.util.Arrays;

public final class VisualVmClassPaths {
  public static final VisualVmClassPaths EMPTY = new VisualVmClassPaths();

  private final String[] classPaths;

  /**
   * @see #EMPTY
   */
  public VisualVmClassPaths() {
    this.classPaths = new String[0];
  }

  public VisualVmClassPaths(final String... classPaths) {
    this.classPaths = Arrays.copyOf(classPaths, classPaths.length);
  }

  @Override
  public boolean equals(final Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    final VisualVmClassPaths that = (VisualVmClassPaths) o;
    return Arrays.equals(classPaths, that.classPaths);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(classPaths);
  }

  @Override
  public String toString() {
    return String.join(",", Arrays.asList(classPaths));
  }

  public static VisualVmClassPaths of(final String... classPaths) {
    return new VisualVmClassPaths(classPaths);
  }

  public static VisualVmClassPaths ofCommaSeparated(final String commasSeparatedClassPath) {
    return new VisualVmClassPaths(commasSeparatedClassPath.split(","));
  }

  public boolean isEmpty() {
    return classPaths.length == 0;
  }

  public String[] values() {
    return Arrays.copyOf(classPaths, classPaths.length);
  }
}
