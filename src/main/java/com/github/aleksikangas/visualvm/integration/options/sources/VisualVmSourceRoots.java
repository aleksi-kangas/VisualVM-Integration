package com.github.aleksikangas.visualvm.integration.options.sources;

import com.github.aleksikangas.visualvm.notifications.VisualVmNotifications;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class VisualVmSourceRoots {
  private final Set<String> sourceRoots;

  public static Optional<VisualVmSourceRoots> resolve(@Nullable final Project project) {
    if (project == null) {
      VisualVmNotifications.notifyInfo(null, "Source roots could not be resolved, since project does not exist.");
      return Optional.empty();
    }
    final Set<String> sourceRoots = new HashSet<>();
    final var moduleManager = ModuleManager.getInstance(project);
    for (final var module : moduleManager.getModules()) {
      sourceRoots.addAll(VisualVmSourceUtils.resolveModuleSourceRoots(module));
      sourceRoots.addAll(VisualVmSourceUtils.resolveModuleLibrarySourceRoots(module));
      sourceRoots.addAll(VisualVmSourceUtils.resolveSdkSourceRoots(module));
    }
    return Optional.of(new VisualVmSourceRoots(sourceRoots));
  }

  @Override
  public String toString() {
    return VisualVmSourceUtils.encodeValue(String.join(File.pathSeparator, sourceRoots));
  }

  private VisualVmSourceRoots(@NotNull final Set<String> sourceRoots) {
    this.sourceRoots = sourceRoots;
  }
}
