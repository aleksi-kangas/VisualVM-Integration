package com.github.aleksikangas.visualvm.actions;

import com.github.aleksikangas.visualvm.notifications.VisualVmNotifications;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ExecutionDataKeys;

import java.util.Optional;

/**
 * A PID aware VisualVM action.
 * @implNote Relies on {@link ExecutionEnvironment#getContentToReuse()} to return a valid
 *         {@link RunContentDescriptor}.
 */
abstract class AbstractPidAwareVisualVmAction extends AbstractVisualVmAction {
  protected final Optional<Long> getPid(final DataContext dataContext) {
    final Optional<Long> pid = Optional.ofNullable(dataContext.getData(ExecutionDataKeys.EXECUTION_ENVIRONMENT))
                                       .map(ExecutionEnvironment::getContentToReuse)
                                       .map(RunContentDescriptor::getProcessHandler)
                                       .filter(OSProcessHandler.class::isInstance)
                                       .map(OSProcessHandler.class::cast)
                                       .map(OSProcessHandler::getProcess)
                                       .map(Process::pid);
    if (pid.isEmpty()) {
      VisualVmNotifications.notifyError(null, "Could not find a valid PID from the current execution environment.");
    }
    return pid;
  }
}
