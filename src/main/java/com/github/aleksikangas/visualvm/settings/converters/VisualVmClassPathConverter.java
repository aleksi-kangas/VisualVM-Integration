package com.github.aleksikangas.visualvm.settings.converters;

import com.github.aleksikangas.visualvm.integration.options.VisualVmClassPaths;
import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VisualVmClassPathConverter extends Converter<VisualVmClassPaths> {
  @Override
  public @NotNull VisualVmClassPaths fromString(@NotNull final String s) {
    return VisualVmClassPaths.ofCommaSeparated(s);
  }

  @Override
  public @Nullable String toString(final VisualVmClassPaths visualVmClassPaths) {
    return visualVmClassPaths.toString();
  }
}
