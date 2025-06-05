package com.github.aleksikangas.ideavisualvm.actions;

import com.github.aleksikangas.ideavisualvm.visualvm.VisualVm;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public final class StartMemorySamplerVisualVmAction extends AbstractPidAwareVisualVmAction {
  @Override
  public void actionPerformed(@NotNull final AnActionEvent e) {
    getPid(e.getDataContext()).ifPresent(pid -> {
      final var visualVmOptions = visualVmOptionsBuilder().withStartMemorySamplerPid(pid)
                                                          .build();
      VisualVm.launch(visualVmOptions);
    });
  }
}
