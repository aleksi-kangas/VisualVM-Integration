package com.github.aleksikangas.visualvm.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

public final class VisualVmSettingsConfigurable implements Configurable {
  @Nullable
  private VisualVmSettingsPanel visualVmSettingsPanel = null;

  @Override
  public @NlsContexts.ConfigurableName String getDisplayName() {
    return "VisualVM";
  }

  @Override
  public JComponent createComponent() {
    if (visualVmSettingsPanel == null) {
      final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
      visualVmSettingsPanel = VisualVmSettingsPanelKt.create(state);
    }
    return visualVmSettingsPanel.getPanel();
  }

  @Override
  public boolean isModified() {
    if (visualVmSettingsPanel == null) return false;
    return visualVmSettingsPanel.getPanel().isModified();
  }

  @Override
  public void apply() {
    if (visualVmSettingsPanel != null) {
      final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
      visualVmSettingsPanel.getPanel().apply();
      state.from(visualVmSettingsPanel.getModel());
    }
  }

  @Override
  public void reset() {
    if (visualVmSettingsPanel != null) {
      visualVmSettingsPanel.getPanel().reset();
    }
  }

  @Override
  public void disposeUIResources() {
    visualVmSettingsPanel = null;
  }
}
