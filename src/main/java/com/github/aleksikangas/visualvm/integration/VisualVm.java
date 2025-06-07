package com.github.aleksikangas.visualvm.integration;

public final class VisualVm {
  /**
   * Attempts to launch VisualVM process with the given options.
   * Practically, a new process may not be started if VisualVM is already running.
   * In such a case, the launch options are forwarded to the existing VisualVM process, and the end result is the same
   * as if a new process had been started.
   * @param visualVmOptions the options for launching VisualVM
   */
  public static void launch(final VisualVmOptions visualVmOptions) {
    final var visualVmProcess = new VisualVmProcess(visualVmOptions);
    visualVmProcess.run();
  }
}
