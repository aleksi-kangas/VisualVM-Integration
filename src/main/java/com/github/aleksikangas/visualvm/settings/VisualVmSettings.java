package com.github.aleksikangas.visualvm.settings;

import com.github.aleksikangas.visualvm.integration.options.appearance.CustomVisualVmLaf;
import com.github.aleksikangas.visualvm.integration.options.appearance.DefaultVisualVmLaf;
import com.github.aleksikangas.visualvm.integration.options.appearance.VisualVmLaf;
import com.github.aleksikangas.visualvm.integration.options.misc.VisualVmClassPaths;
import com.github.aleksikangas.visualvm.integration.options.sources.VisualVmSourceConfig;
import com.github.aleksikangas.visualvm.settings.converters.VisualVmClassPathConverter;
import com.github.aleksikangas.visualvm.settings.converters.VisualVmLafConverter;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.NotNull;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

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
    @Attribute
    private String executablePath = "";
    @Attribute
    private boolean ideAsSourceViewer = true;
    @Attribute
    private boolean automaticSourceRoots = true;
    @Attribute
    private boolean automaticPid = true;
    // Appearance
    @Attribute
    private int fontSize = 12;
    @Attribute(converter = VisualVmLafConverter.class)
    private VisualVmLaf laf = DefaultVisualVmLaf.NONE;
    @Attribute
    private boolean windowToFront = false;
    // JDK
    @Attribute
    private String jdkHomePath = "";
    @Attribute
    private String jvmOptions = "";
    // Directories
    @Attribute
    private String cacheDirPath = "";
    @Attribute
    public String userDirPath = "";
    // Miscellaneous
    @Attribute(converter = VisualVmClassPathConverter.class)
    private VisualVmClassPaths prependClassPath = VisualVmClassPaths.EMPTY;
    @Attribute(converter = VisualVmClassPathConverter.class)
    private VisualVmClassPaths appendClassPath = VisualVmClassPaths.EMPTY;

    public void from(final VisualVmSettingsPanelModel model) {
      executablePath = model.getExecutablePath();
      ideAsSourceViewer = model.getIdeAsSourceViewer();
      automaticPid = model.getAutomaticPid();
      automaticSourceRoots = model.getAutomaticSourceRoots();
      fontSize = model.getFontSize();
      laf = model.getLaf();
      windowToFront = model.getWindowToFront();
      jdkHomePath = model.getJdkHomePath();
      jvmOptions = model.getJvmOptions();
      userDirPath = model.getUserDirPath();
      cacheDirPath = model.getCacheDirPath();
      prependClassPath = VisualVmClassPaths.ofCommaSeparated(model.getPrependClassPath());
      appendClassPath = VisualVmClassPaths.ofCommaSeparated(model.getAppendClassPath());
    }

    public boolean isValid() {
      return isExecutablePathValid();
    }

    /**
     * @return the non-blank path of the executable file, or empty
     */
    public Optional<String> executablePath() {
      return Optional.ofNullable(executablePath)
                     .filter(p -> !p.isBlank())
                     .map(String::new);
    }

    public boolean ideAsSourceViewer() {
      return ideAsSourceViewer;
    }

    public boolean automaticSourceRoots() {
      return automaticSourceRoots;
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

    public boolean automaticPid() {
      return automaticPid;
    }

    public int fontSize() {
      return fontSize;
    }

    /**
     * @return the override VisualVM LAF, or empty
     */
    public Optional<VisualVmLaf> laf() {
      return Optional.ofNullable(laf)
                     .filter(laf -> (laf instanceof DefaultVisualVmLaf && laf != DefaultVisualVmLaf.NONE)
                             || laf instanceof CustomVisualVmLaf);
    }

    public boolean windowToFront() {
      return windowToFront;
    }

    /**
     * @return the non-blank path of the jdk home directory, or empty
     */
    public Optional<String> jdkHomePath() {
      return Optional.ofNullable(jdkHomePath)
                     .filter(p -> !p.isBlank())
                     .map(String::new);
    }

    /**
     * @return the non-blank JVM options, or empty
     */
    public Optional<String> jvmOptions() {
      return Optional.ofNullable(jvmOptions)
                     .filter(p -> !p.isBlank())
                     .map(String::new);
    }

    /**
     * @return the non-blank path of the cache directory, or empty
     */
    public Optional<String> cacheDirPath() {
      return Optional.ofNullable(cacheDirPath)
                     .filter(p -> !p.isBlank())
                     .map(String::new);
    }

    /**
     * @return the non-blank path of the user directory, or empty
     */
    public Optional<String> userDirPath() {
      return Optional.ofNullable(userDirPath)
                     .filter(p -> !p.isBlank())
                     .map(String::new);
    }

    /**
     * @return the non-empty prepend classpath, or empty
     */
    public Optional<VisualVmClassPaths> prependClassPath() {
      return Optional.ofNullable(prependClassPath)
                     .filter(cp -> !cp.isEmpty())
                     .map(VisualVmClassPaths::copyOf);
    }

    public Optional<VisualVmClassPaths> appendClassPath() {
      return Optional.ofNullable(appendClassPath)
                     .filter(cp -> !cp.isEmpty())
                     .map(VisualVmClassPaths::copyOf);
    }

    private boolean isExecutablePathValid() {
      try {
        final var path = Path.of(executablePath);
        return path.toFile().exists();
      } catch (final InvalidPathException e) {
        return false;
      }
    }
  }
}
