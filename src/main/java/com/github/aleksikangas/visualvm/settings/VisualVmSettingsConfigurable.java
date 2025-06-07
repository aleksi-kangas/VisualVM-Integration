package com.github.aleksikangas.visualvm.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

public final class VisualVmSettingsConfigurable implements Configurable {
  @Nullable
  private VisualVmSettingsComponent settingsComponent = null;

  @Override
  public @NlsContexts.ConfigurableName String getDisplayName() {
    return "VisualVM";
  }

  @Override
  public JComponent createComponent() {
    if (settingsComponent == null) {
      settingsComponent = new VisualVmSettingsComponent();
    }
    return settingsComponent.getPanel();
  }

  @Override
  public boolean isModified() {
    if (settingsComponent == null) return false;
    final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
    return !Objects.equals(settingsComponent.getExecutablePath(), state.executablePath)
            || settingsComponent.overrideJdk() != state.overrideJdk
            || !Objects.equals(settingsComponent.getJdkHome(), state.jdkHome)
            && settingsComponent.windowToFront() != state.windowToFront;
  }

  @Override
  public void apply() {
    if (settingsComponent != null) {
      final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
      state.executablePath = settingsComponent.getExecutablePath();
      state.overrideJdk = settingsComponent.overrideJdk();
      state.jdkHome = settingsComponent.getJdkHome();
      state.windowToFront = settingsComponent.windowToFront();
    }
  }

  @Override
  public void reset() {
    if (settingsComponent != null) {
      final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
      settingsComponent.setExecutablePath(state.executablePath);
      settingsComponent.setOverrideJdk(state.overrideJdk);
      settingsComponent.setJdkHome(state.jdkHome);
      settingsComponent.setWindowToFront(state.windowToFront);
    }
  }

  @Override
  public void disposeUIResources() {
    settingsComponent = null;
  }
}
