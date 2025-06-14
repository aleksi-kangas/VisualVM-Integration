package com.github.aleksikangas.visualvm.integration.options;

import com.github.aleksikangas.visualvm.notifications.VisualVmNotifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

public class VisualVmSourceConfig {
  private final Path sourceConfigPath;

  public static Optional<VisualVmSourceConfig> resolve(@Nullable final Project project) {
    try {
      final File tempFile = FileUtil.createTempFile("visualvm-source-config", ".properties");
      final Properties sourceProperties = new Properties();
      VisualVmSourceRoots.resolve(project)
                         .ifPresent(sourceRoots -> {
                           sourceProperties.setProperty("source-roots", sourceRoots.toString());
                         });
      VisualVmSourceViewer.resolve()
                          .ifPresent(sourceViewer -> {
                            sourceProperties.setProperty("source-viewer", sourceViewer.toString());
                          });
      try (final var writer = Files.newBufferedWriter(tempFile.toPath(), StandardCharsets.UTF_8)) {
        sourceProperties.store(writer, null);
      }
      return Optional.of(new VisualVmSourceConfig(tempFile.toPath()));
    } catch (final IOException e) {
      VisualVmNotifications.notifyError(project, "Failed to resolve source roots+viewer config");
      return Optional.empty();
    }
  }

  public Path getSourceConfigPath() {
    return sourceConfigPath;
  }

  private VisualVmSourceConfig(final Path sourceConfigPath) {
    this.sourceConfigPath = sourceConfigPath;
  }
}
