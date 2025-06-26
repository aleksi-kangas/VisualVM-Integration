package com.github.aleksikangas.visualvm.integration.options.appearance;

import org.jetbrains.annotations.NotNull;

public record DefaultVisualVmLaf(@NotNull Variant variant) implements VisualVmLaf {
  public enum Variant {
    NONE,
    AQUA,
    GTK,
    METAL,
    WINDOWS;

    @Override
    public @NotNull String toString() {
      return switch (this) {
        case NONE -> "-";
        case AQUA -> "Aqua";
        case GTK -> "GTK";
        case METAL -> "Metal";
        case WINDOWS -> "Windows";
      };
    }
  }

  public static DefaultVisualVmLaf NONE = new DefaultVisualVmLaf(Variant.NONE);
  public static DefaultVisualVmLaf AQUA = new DefaultVisualVmLaf(Variant.AQUA);
  public static DefaultVisualVmLaf GTK = new DefaultVisualVmLaf(Variant.GTK);
  public static DefaultVisualVmLaf METAL = new DefaultVisualVmLaf(Variant.METAL);
  public static DefaultVisualVmLaf WINDOWS = new DefaultVisualVmLaf(Variant.WINDOWS);

  @Override
  public String value() {
    return switch (variant) {
      case NONE -> "";
      case AQUA -> "Aqua";
      case GTK -> "GTK";
      case METAL -> "Metal";
      case WINDOWS -> "Windows";
    };
  }

  @Override
  public @NotNull String toString() {
    return value();
  }
}
