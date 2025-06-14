package com.github.aleksikangas.visualvm.settings.converters;

import com.github.aleksikangas.visualvm.integration.options.VisualVmLaf;
import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VisualVmLafConverter extends Converter<VisualVmLaf> {
  @Override
  public @Nullable VisualVmLaf fromString(@NotNull final String s) {
    return VisualVmLaf.of(s);
  }

  @Override
  public @Nullable String toString(@NotNull final VisualVmLaf visualVmLaf) {
    return visualVmLaf.toString();
  }
}
