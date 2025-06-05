package com.github.aleksikangas.ideavisualvm.actions;

import com.github.aleksikangas.ideavisualvm.settings.VisualVmSettings;
import com.github.aleksikangas.ideavisualvm.visualvm.VisualVmOptions;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

abstract class AbstractVisualVmAction extends AnAction {
  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.BGT;
  }

  protected VisualVmOptions.Builder visualVmOptionsBuilder() {
    final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
    return new VisualVmOptions
            .Builder(state.executablePath)
            .withJdkHome(state.overrideJdk ? state.jdkHome : null)
            .withWindowToFront(state.windowToFront ? true : null);
  }
}
