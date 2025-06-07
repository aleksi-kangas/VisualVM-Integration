package com.github.aleksikangas.visualvm.integration;

public final class VisualVm {
  public static void launch(final VisualVmOptions visualVmOptions) {
    final var visualVmProcess = new VisualVmProcess(visualVmOptions);
    visualVmProcess.run();
  }
}
