package com.github.aleksikangas.ideavisualvm.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@State(name = "com.github.aleksikangas.ideavisualvm.settings.VisualVmSettings",
        storages = @Storage("ideaVisualVmPluginSettings.xml"))
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
  }
}
