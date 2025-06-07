package com.github.aleksikangas.visualvm.actions;

import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ExecutionDataKeys;

import java.util.Optional;

abstract class AbstractPidAwareVisualVmAction extends AbstractVisualVmAction {
  protected final Optional<Long> getPid(final DataContext dataContext) {
    return Optional.ofNullable(dataContext.getData(ExecutionDataKeys.EXECUTION_ENVIRONMENT))
                   .map(ExecutionEnvironment::getContentToReuse)
                   .map(RunContentDescriptor::getProcessHandler)
                   .filter(OSProcessHandler.class::isInstance)
                   .map(OSProcessHandler.class::cast)
                   .map(OSProcessHandler::getProcess)
                   .map(Process::pid);
  }
}
