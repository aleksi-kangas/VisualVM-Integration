package com.github.aleksikangas.visualvm.actions;

import com.github.aleksikangas.visualvm.integration.process.VisualVm;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public final class HeapDumpVisualVmAction extends AbstractPidAwareVisualVmAction {
  @Override
  public void actionPerformed(@NotNull final AnActionEvent e) {
    selectPid(e).thenAccept(pid -> {
      final var visualVmOptions = visualVmOptionsBuilder(e).withHeapDumpPid(pid)
                                                           .build();
      VisualVm.launch(visualVmOptions);
    });
  }
}
