package com.github.aleksikangas.visualvm.integration.options.sources;

import com.github.aleksikangas.visualvm.notifications.VisualVmNotifications;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.util.system.CpuArch;

import java.nio.file.Path;
import java.util.Optional;

import static com.github.aleksikangas.visualvm.integration.options.sources.VisualVmSourceUtils.encodeValue;

public final class VisualVmSourceViewer {
  private final Path ideExecutablePath;

  public static Optional<VisualVmSourceViewer> resolve() {
    final String ideScriptName = ApplicationNamesInfo.getInstance().getScriptName();
    final Path binPath = Path.of(PathManager.getBinPath());
    final Path homePath = Path.of(PathManager.getHomePath());
    Path idePath = null;
    if (SystemInfo.isMac) {
      idePath = homePath.resolve("MacOS").resolve(ideScriptName);
    } else if (SystemInfo.isUnix) {
      idePath = binPath.resolve(ideScriptName + ".sh");
    } else if (SystemInfo.isWindows) {
      idePath = binPath.resolve(ideScriptName + (CpuArch.CURRENT.width == 64 ? "64" : "") + ".exe");
    }
    if (idePath != null) {
      return Optional.of(new VisualVmSourceViewer(idePath));
    }
    VisualVmNotifications.notifyWarning(null, "Failed to resolve IDE as source viewer.");
    return Optional.empty();
  }

  @Override
  public String toString() {
    // Extra quotes are needed to prevent the path from being interpreted as more than one argument.
    return encodeValue("\"" + ideExecutablePath.toString() + "\"") + " --line {line} {file}";
  }

  private VisualVmSourceViewer(final Path ideExecutablePath) {
    this.ideExecutablePath = ideExecutablePath;
  }
}
