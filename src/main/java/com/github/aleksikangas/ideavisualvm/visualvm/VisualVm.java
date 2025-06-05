package com.github.aleksikangas.ideavisualvm.visualvm;

public final class VisualVm {
  public static void launch(final VisualVmOptions visualVmOptions) {
    final var visualVmProcess = new VisualVmProcess(visualVmOptions);
    visualVmProcess.run();
  }
}
