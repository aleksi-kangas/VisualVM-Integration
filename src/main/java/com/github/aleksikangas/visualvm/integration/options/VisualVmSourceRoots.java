package com.github.aleksikangas.visualvm.integration.options;

import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class VisualVmSourceRoots {
  private final Set<VirtualFile> sourceRoots;

  public static Optional<VisualVmSourceRoots> resolve(@Nullable final Project project) {
    if (project == null) {
      return Optional.empty();
    }
    final Set<VirtualFile> sourceRoots = new HashSet<>();
    final var moduleManager = ModuleManager.getInstance(project);
    for (final var module : moduleManager.getModules()) {
      final var moduleRootManager = ModuleRootManager.getInstance(module);
      sourceRoots.addAll(List.of(moduleRootManager.getSourceRoots(false)));
    }
    return Optional.of(new VisualVmSourceRoots(sourceRoots));
  }

  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
    for (final var sourceRoot : sourceRoots) {
      stringBuilder.append(sourceRoot.getPath())
                   .append(File.pathSeparator);
    }
    if (!sourceRoots.isEmpty()) {
      stringBuilder.deleteCharAt(stringBuilder.length() - 1);  // Remove the last path separator
    }
    return VisualVmSourceUtils.encodeValue(stringBuilder.toString());
  }

  private VisualVmSourceRoots(@NotNull final Set<VirtualFile> sourceRoots) {
    this.sourceRoots = sourceRoots;
  }
}
