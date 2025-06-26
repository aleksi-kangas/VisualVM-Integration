package com.github.aleksikangas.visualvm.actions;

import com.github.aleksikangas.visualvm.notifications.VisualVmNotifications;
import com.github.aleksikangas.visualvm.process.JpsProcess;
import com.github.aleksikangas.visualvm.process.ProcessUtils;
import com.github.aleksikangas.visualvm.settings.VisualVmSettings;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.actionSystem.*;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * A PID aware VisualVM action.
 * @implNote Relies on {@link ExecutionEnvironment#getContentToReuse()} to return a valid
 *         {@link RunContentDescriptor}.
 */
abstract class AbstractPidAwareVisualVmAction extends AbstractVisualVmAction {
  protected final CompletableFuture<Long> selectPid(final AnActionEvent e) {
    final VisualVmSettings.State state = Objects.requireNonNull(VisualVmSettings.getInstance().getState());
    if (state.automaticPid()) {
      return Optional.ofNullable(e.getDataContext()
                                  .getData(ExecutionDataKeys.EXECUTION_ENVIRONMENT))
                     .map(ExecutionEnvironment::getContentToReuse)
                     .map(RunContentDescriptor::getProcessHandler)
                     .filter(OSProcessHandler.class::isInstance)
                     .map(OSProcessHandler.class::cast)
                     .map(OSProcessHandler::getProcess)
                     .map(Process::pid)
                     .map(CompletableFuture::completedFuture)
                     .orElseGet(() -> askUserForPid(e));
    }
    return askUserForPid(e);
  }

  private CompletableFuture<Long> askUserForPid(final AnActionEvent e) {
    final CompletableFuture<Long> selectionFuture = new CompletableFuture<>();
    final DefaultActionGroup actionGroup = new DefaultActionGroup();
    final List<JpsProcess> jpsProcesses = ProcessUtils.listJpsProcesses();
    if (jpsProcesses.isEmpty()) {
      VisualVmNotifications.notifyWarning(null, "No Java processes found.");
      selectionFuture.completeExceptionally(new IllegalStateException("No Java processes found."));
      return selectionFuture;
    }
    for (final JpsProcess jpsProcess : jpsProcesses) {
      actionGroup.add(new AnAction(jpsProcess.toString()) {
        @Override
        public void actionPerformed(@NotNull final AnActionEvent anActionEvent) {
          selectionFuture.complete(jpsProcess.pid());
        }
      });
    }
    if (e.getInputEvent() instanceof MouseEvent mouseEvent) {
      ActionPopupMenu popupMenu = ActionManager.getInstance()
                                               .createActionPopupMenu(ActionPlaces.TOOLWINDOW_TOOLBAR_BAR, actionGroup);
      popupMenu.getComponent().show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
    } else {
      VisualVmNotifications.notifyWarning(null, "No Java processes found.");
      selectionFuture.completeExceptionally(new IllegalStateException("No Java processes found."));
    }
    return selectionFuture;
  }
}
