package com.github.aleksikangas.visualvm.integration.options;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public final class VisualVmLaf {
  public static final VisualVmLaf NONE = new VisualVmLaf("");
  public static final VisualVmLaf METAL = new VisualVmLaf("Metal");
  public static final VisualVmLaf WINDOWS = new VisualVmLaf("Windows");
  public static final VisualVmLaf AQUA = new VisualVmLaf("Aqua");
  public static final VisualVmLaf GTK = new VisualVmLaf("GTK");

  public enum Variant {
    NONE,
    AQUA,
    GTK,
    METAL,
    WINDOWS,
    CUSTOM;

    @Override
    public String toString() {
      return switch (this) {
        case NONE -> "-";
        case AQUA -> "Aqua";
        case GTK -> "GTK";
        case METAL -> "Metal";
        case WINDOWS -> "Windows";
        case CUSTOM -> "Custom";
      };
    }
  }

  @NotNull
  private final String value;

  /**
   * @see #NONE
   */
  public VisualVmLaf() {
    this(NONE.value);
  }

  /**
   * @see #custom(String)
   */
  private VisualVmLaf(@NotNull final String value) {
    this.value = value;
  }

  public static VisualVmLaf of(@NotNull final String value) {
    if (value.isEmpty()) return NONE;
    if (AQUA.value.equals(value)) return AQUA;
    if (GTK.value.equals(value)) return GTK;
    if (METAL.value.equals(value)) return METAL;
    if (WINDOWS.value.equals(value)) return WINDOWS;
    return custom(value);
  }

  public static VisualVmLaf[] knownValues() {
    return new VisualVmLaf[]{NONE, METAL, WINDOWS, AQUA, GTK};
  }

  public static VisualVmLaf custom(@NotNull final String className) {
    return new VisualVmLaf(Objects.requireNonNull(className));
  }

  @Override
  public boolean equals(final Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    final VisualVmLaf that = (VisualVmLaf) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  @Override
  public String toString() {
    return value;
  }

  public boolean isCustom() {
    return Arrays.stream(knownValues()).noneMatch(v -> v.equals(this));
  }

  public Variant variant() {
    if (value.isEmpty()) return Variant.NONE;
    if (METAL.value.equals(value)) return Variant.METAL;
    if (WINDOWS.value.equals(value)) return Variant.WINDOWS;
    if (GTK.value.equals(value)) return Variant.GTK;
    if (AQUA.value.equals(value)) return Variant.AQUA;
    return Variant.CUSTOM;
  }
}
