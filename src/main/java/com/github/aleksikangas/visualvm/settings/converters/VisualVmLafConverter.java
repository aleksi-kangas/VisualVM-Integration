package com.github.aleksikangas.visualvm.settings.converters;

import com.github.aleksikangas.visualvm.integration.options.appearance.CustomVisualVmLaf;
import com.github.aleksikangas.visualvm.integration.options.appearance.DefaultVisualVmLaf;
import com.github.aleksikangas.visualvm.integration.options.appearance.VisualVmLaf;
import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VisualVmLafConverter extends Converter<VisualVmLaf> {
  @Override
  public @Nullable VisualVmLaf fromString(@NotNull final String s) {
    if (s.isBlank()) return DefaultVisualVmLaf.NONE;
    if (DefaultVisualVmLaf.AQUA.value().equals(s)) return DefaultVisualVmLaf.AQUA;
    if (DefaultVisualVmLaf.GTK.value().equals(s)) return DefaultVisualVmLaf.GTK;
    if (DefaultVisualVmLaf.METAL.value().equals(s)) return DefaultVisualVmLaf.METAL;
    if (DefaultVisualVmLaf.WINDOWS.value().equals(s)) return DefaultVisualVmLaf.WINDOWS;
    return new CustomVisualVmLaf(s);
  }

  @Override
  public @NotNull String toString(@NotNull final VisualVmLaf visualVmLaf) {
    return visualVmLaf.value();
  }
}
