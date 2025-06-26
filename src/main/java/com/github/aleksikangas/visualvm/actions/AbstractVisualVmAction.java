package com.github.aleksikangas.visualvm.actions;

import com.github.aleksikangas.visualvm.integration.options.VisualVmOptions;
import com.github.aleksikangas.visualvm.integration.options.sources.VisualVmSourceConfig;
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
  protected VisualVmOptions.Builder visualVmOptionsBuilder(final AnActionEvent e) {
    final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
    final var optionsBuilder = new VisualVmOptions.Builder(state.executablePath()
                                                                .orElseThrow(() -> new IllegalStateException(
                                                                        "VisualVM executable path is not set")));
    VisualVmSourceConfig.resolve(e.getProject(), state.sourceConfigParameters())
                        .ifPresent(optionsBuilder::withSourceConfig);
    if (state.windowToFront()) {
      optionsBuilder.withWindowToFront(true);
    }
    state.laf().ifPresent(optionsBuilder::withLaf);
    state.jdkHomePath().ifPresent(optionsBuilder::withJdkHome);
    state.cacheDirPath().ifPresent(optionsBuilder::withCacheDir);
    state.userDirPath().ifPresent(optionsBuilder::withUserDir);
    state.prependClassPath().ifPresent(optionsBuilder::withPrependClassPath);
    state.appendClassPath().ifPresent(optionsBuilder::withAppendClassPath);
    return optionsBuilder;
  }
}
