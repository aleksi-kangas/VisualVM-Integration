package com.github.aleksikangas.visualvm.actions;

import com.github.aleksikangas.visualvm.integration.options.VisualVmOptions;
import com.github.aleksikangas.visualvm.integration.process.VisualVm;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public final class LaunchVisualVmAction extends AbstractVisualVmAction {
  @Override
  public void actionPerformed(@NotNull final AnActionEvent e) {
    final VisualVmOptions visualVmOptions = visualVmOptionsBuilder(e).build();
    VisualVm.launch(visualVmOptions);
  }
}
