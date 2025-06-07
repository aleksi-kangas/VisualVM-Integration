package com.github.aleksikangas.visualvm.actions;

import com.github.aleksikangas.visualvm.integration.VisualVmOptions;
import com.github.aleksikangas.visualvm.settings.VisualVmSettings;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Abstract class for actions that can be executed in the context of VisualVM.
 */
abstract class AbstractVisualVmAction extends AnAction {
  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.BGT;
  }

  @Override
  public void update(@NotNull final AnActionEvent e) {
    final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
    e.getPresentation().setEnabled(state.isValid());
  }

  /**
   * @return A preconfigured {@link VisualVmOptions.Builder} with the common settings applied.
   */
  protected VisualVmOptions.Builder visualVmOptionsBuilder() {
    final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
    return new VisualVmOptions
            .Builder(state.executablePath)
            .withJdkHome(state.overrideJdk ? state.jdkHome : null)
            .withWindowToFront(state.windowToFront ? true : null)
            .withLaf(state.overrideLaf ? state.laf : null);
  }
}
