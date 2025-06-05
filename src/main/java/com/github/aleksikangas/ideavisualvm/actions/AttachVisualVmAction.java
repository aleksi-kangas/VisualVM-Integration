package com.github.aleksikangas.ideavisualvm.actions;

import com.github.aleksikangas.ideavisualvm.settings.VisualVmSettings;
import com.github.aleksikangas.ideavisualvm.visualvm.VisualVm;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class AttachVisualVmAction extends AbstractPidAwareVisualVmAction {
  @Override
  public void actionPerformed(@NotNull final AnActionEvent e) {
    getPid(e.getDataContext()).ifPresent(pid -> {
      final var visualVmOptions = visualVmOptionsBuilder().withOpenPid(pid)
                                                          .build();
      VisualVm.launch(visualVmOptions);
    });
  }

  @Override
  public void update(@NotNull final AnActionEvent e) {
    final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
    e.getPresentation().setEnabled(state.isValid());
  }
}
