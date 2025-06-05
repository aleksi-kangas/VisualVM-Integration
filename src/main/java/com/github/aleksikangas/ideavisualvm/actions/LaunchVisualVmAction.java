package com.github.aleksikangas.ideavisualvm.actions;

import com.github.aleksikangas.ideavisualvm.settings.VisualVmSettings;
import com.github.aleksikangas.ideavisualvm.visualvm.VisualVm;
import com.github.aleksikangas.ideavisualvm.visualvm.VisualVmOptions;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class LaunchVisualVmAction extends AnAction {
  @Override
  public void actionPerformed(@NotNull final AnActionEvent e) {
    final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
    final var visualVmOptions = new VisualVmOptions
            .Builder(state.executablePath)
            .build();
    VisualVm.launch(visualVmOptions);
  }

  @Override
  public void update(@NotNull final AnActionEvent e) {
    // TODO: Disable when VisualVM configuration is invalid.
    // e.getPresentation().setEnabledAndVisible(condition);
  }

  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.BGT;
  }
}
