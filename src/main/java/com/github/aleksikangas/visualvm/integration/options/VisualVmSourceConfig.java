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

public final class VisualVmSourceConfig {
  public enum Parameters {
    NONE,
    SOURCE_ROOTS,
    SOURCE_VIEWER,
    BOTH
  }

  private static final String SOURCE_CONFIG_FILENAME_PREFIX = "visualvm-source-config";
  private static final String SOURCE_CONFIG_FILENAME_SUFFIX = ".properties";
  private static final String SOURCE_ROOTS_PROPERTY = "source-roots";
  private static final String SOURCE_VIEWER_PROPERTY = "source-viewer";

  private final Path sourceConfigPath;

  public static Optional<VisualVmSourceConfig> resolve(@Nullable final Project project, final Parameters parameters) {
    if (parameters == Parameters.NONE)
      return Optional.empty();
    try {
      final File tempFile = FileUtil.createTempFile(SOURCE_CONFIG_FILENAME_PREFIX, SOURCE_CONFIG_FILENAME_SUFFIX);
      final Properties sourceProperties = new Properties();

      if (parameters == Parameters.BOTH || parameters == Parameters.SOURCE_ROOTS) {
        VisualVmSourceRoots.resolve(project)
                           .ifPresent(sourceRoots -> sourceProperties
                                   .setProperty(SOURCE_ROOTS_PROPERTY, sourceRoots.toString()));
      }
      if (parameters == Parameters.BOTH || parameters == Parameters.SOURCE_VIEWER) {

        VisualVmSourceViewer.resolve()
                            .ifPresent(sourceViewer -> sourceProperties
                                    .setProperty(SOURCE_VIEWER_PROPERTY, sourceViewer.toString()));
      }
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
