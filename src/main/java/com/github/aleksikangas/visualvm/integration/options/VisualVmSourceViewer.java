package com.github.aleksikangas.visualvm.integration.options;

import com.github.aleksikangas.visualvm.notifications.VisualVmNotifications;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.util.system.CpuArch;

import java.nio.file.Path;
import java.util.Optional;

public class VisualVmSourceViewer {
  private final Path ideExecutablePath;

  public static Optional<VisualVmSourceViewer> resolve() {
    final String ideScriptName = ApplicationNamesInfo.getInstance().getScriptName();
    final Path binPath = Path.of(PathManager.getBinPath());
    final Path homePath = Path.of(PathManager.getHomePath());
    if (SystemInfo.isMac) {
      return Optional.of(new VisualVmSourceViewer(homePath.resolve("MacOS").resolve(ideScriptName)));
    } else if (SystemInfo.isUnix) {
      return Optional.of(new VisualVmSourceViewer(binPath.resolve(ideScriptName + ".sh")));
    } else if (SystemInfo.isWindows) {
      return Optional.of(new VisualVmSourceViewer(binPath.resolve(ideScriptName + (CpuArch.CURRENT.width == 64 ? "64" : "") + ".exe")));
    }
    VisualVmNotifications.notifyWarning(null, "Failed to resolve IDE as source viewer.");
    return Optional.empty();
  }

  @Override
  public String toString() {
    return ideExecutablePath.toString() + " --line {line} {file}";
  }

  private VisualVmSourceViewer(final Path ideExecutablePath) {
    this.ideExecutablePath = ideExecutablePath;
  }
}
