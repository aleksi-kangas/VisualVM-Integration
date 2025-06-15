package com.github.aleksikangas.visualvm.settings;

import com.github.aleksikangas.visualvm.integration.options.appearance.VisualVmLaf;
import com.github.aleksikangas.visualvm.integration.options.misc.VisualVmClassPaths;
import com.github.aleksikangas.visualvm.integration.options.sources.VisualVmSourceConfig;
import com.github.aleksikangas.visualvm.settings.converters.VisualVmClassPathConverter;
import com.github.aleksikangas.visualvm.settings.converters.VisualVmLafConverter;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.annotations.OptionTag;
import org.jetbrains.annotations.NotNull;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Objects;

@State(name = "com.github.aleksikangas.visualvm.settings.VisualVmSettings",
        storages = @Storage("VisualVMIntegrationPluginSettings.xml"))
public final class VisualVmSettings implements PersistentStateComponent<VisualVmSettings.State> {
  private State state = new State();

  public static VisualVmSettings getInstance() {
    return ApplicationManager.getApplication().getService(VisualVmSettings.class);
  }

  @Override
  public @NotNull VisualVmSettings.State getState() {
    return state;
  }

  @Override
  public void loadState(@NotNull final State state) {
    this.state = Objects.requireNonNull(state);
  }

  public static final class State {
    // General
    public String executablePath = "";
    public boolean ideAsSourceViewer = true;
    public boolean automaticPid = true;
    public boolean automaticSourceRoots = true;
    // Appearance
    public boolean windowToFront = false;
    @OptionTag(converter = VisualVmLafConverter.class)
    public VisualVmLaf laf = VisualVmLaf.NONE;
    // JDK
    public boolean overrideJdk = false;
    public String jdkHomePath = "";
    // Miscellaneous
    @OptionTag(converter = VisualVmClassPathConverter.class)
    public VisualVmClassPaths prependClassPath = VisualVmClassPaths.EMPTY;
    @OptionTag(converter = VisualVmClassPathConverter.class)
    public VisualVmClassPaths appendClassPath = VisualVmClassPaths.EMPTY;

    public void from(final VisualVmSettingsPanelModel model) {
      executablePath = model.getExecutablePath();
      ideAsSourceViewer = model.getIdeAsSourceViewer();
      automaticPid = model.getAutomaticPid();
      automaticSourceRoots = model.getAutomaticSourceRoots();
      windowToFront = model.getWindowToFront();
      laf = model.getLaf();
      overrideJdk = model.getOverrideJdk();
      jdkHomePath = model.getJdkHomePath();
      prependClassPath = VisualVmClassPaths.ofCommaSeparated(model.getPrependClassPath());
      appendClassPath = VisualVmClassPaths.ofCommaSeparated(model.getAppendClassPath());
    }

    public boolean isValid() {
      return isExecutablePathValid() && isJdkHomePathValid();
    }

    public VisualVmSourceConfig.Parameters sourceConfigParameters() {
      if (ideAsSourceViewer && automaticSourceRoots)
        return VisualVmSourceConfig.Parameters.BOTH;
      if (automaticSourceRoots)
        return VisualVmSourceConfig.Parameters.SOURCE_ROOTS;
      if (ideAsSourceViewer)
        return VisualVmSourceConfig.Parameters.SOURCE_VIEWER;
      return VisualVmSourceConfig.Parameters.NONE;
    }

    private boolean isExecutablePathValid() {
      if (executablePath == null)
        return false;
      try {
        final var path = Path.of(executablePath);
        return path.toFile().exists();
      } catch (final InvalidPathException e) {
        return false;
      }
    }

    private boolean isJdkHomePathValid() {
      if (!overrideJdk)
        return true;
      if (jdkHomePath == null)
        return false;
      try {
        final var path = Path.of(jdkHomePath);
        return path.toFile().exists();
      } catch (final InvalidPathException e) {
        return false;
      }
    }
  }
}
