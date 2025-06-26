package com.github.aleksikangas.visualvm.settings.converters;

import com.github.aleksikangas.visualvm.integration.options.misc.VisualVmClassPaths;
import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;

public final class VisualVmClassPathConverter extends Converter<VisualVmClassPaths> {
  @Override
  public @NotNull VisualVmClassPaths fromString(@NotNull final String s) {
    return VisualVmClassPaths.ofCommaSeparated(s);
  }

  @Override
  public @NotNull String toString(@NotNull final VisualVmClassPaths visualVmClassPaths) {
    return visualVmClassPaths.toString();
  }
}
