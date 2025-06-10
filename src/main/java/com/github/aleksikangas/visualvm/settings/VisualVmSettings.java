package com.github.aleksikangas.visualvm.settings;

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
    public String executablePath = "";
    public boolean overrideJdk = false;
    public String jdkHome = "";
    public boolean windowToFront = false;
    @OptionTag(converter = VisualVmLafConverter.class)
    public VisualVmLaf laf = VisualVmLaf.NONE;
    @OptionTag(converter = VisualVmClassPathConverter.class)
    public VisualVmClassPaths prependClassPath = VisualVmClassPaths.EMPTY;
    @OptionTag(converter = VisualVmClassPathConverter.class)
    public VisualVmClassPaths appendClassPath = VisualVmClassPaths.EMPTY;

    @Override
    public boolean equals(final Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      final State state = (State) o;
      return overrideJdk == state.overrideJdk
              && windowToFront == state.windowToFront
              && Objects.equals(executablePath, state.executablePath)
              && Objects.equals(jdkHome, state.jdkHome)
              && Objects.equals(laf, state.laf)
              && Objects.equals(prependClassPath, state.prependClassPath)
              && Objects.equals(appendClassPath, state.appendClassPath);
    }

    @Override
    public int hashCode() {
      return Objects.hash(executablePath, overrideJdk, jdkHome, windowToFront, laf, prependClassPath, appendClassPath);
    }

    public boolean isValid() {
      return isExecutablePathValid() && isJdkHomeValid();
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

    private boolean isJdkHomeValid() {
      if (!overrideJdk)
        return true;
      if (jdkHome == null)
        return false;
      try {
        final var path = Path.of(jdkHome);
        return path.toFile().exists();
      } catch (final InvalidPathException e) {
        return false;
      }
    }
  }
}
