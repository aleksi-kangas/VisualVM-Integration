package com.github.aleksikangas.visualvm.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class VisualVmProcess implements Runnable {
  private final VisualVmOptions options;

  VisualVmProcess(final VisualVmOptions options) {
    this.options = options;
  }

  @Override
  public void run() {
    final var processBuilder = new ProcessBuilder(buildCommandList());
    try {
      processBuilder.start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<String> buildCommandList() {
    final List<String> commandList = new ArrayList<>();
    commandList.add(options.executable());
    options.jdkHome().ifPresent(jdkHome -> {
      commandList.add(VisualVmOptions.JDK_HOME);
      commandList.add(jdkHome);
    });
    options.openFile().ifPresent(openFile -> {
      commandList.add(VisualVmOptions.OPEN_FILE);
      commandList.add(openFile);
    });
    options.openPid().ifPresent(openPid -> {
      commandList.add(VisualVmOptions.OPEN_PID);
      commandList.add(String.valueOf(openPid));
    });
    options.openJmx().ifPresent(openJmx -> {
      commandList.add(VisualVmOptions.OPEN_JMX);
      commandList.add(openJmx);
    });
    options.threadDumpPid().ifPresent(threadDumpPid -> {
      commandList.add(VisualVmOptions.THREAD_DUMP);
      commandList.add(String.valueOf(threadDumpPid));
    });
    options.heapDumpPid().ifPresent(heapDumpPid -> {
      commandList.add(VisualVmOptions.HEAP_DUMP);
      commandList.add(String.valueOf(heapDumpPid));
    });
    options.startCpuSamplerPid().ifPresent(startCpuSamplerPid -> {
      commandList.add(VisualVmOptions.START_CPU_SAMPLER);
      commandList.add(String.valueOf(startCpuSamplerPid));
    });
    options.startMemorySamplerPid().ifPresent(startMemorySamplerPid -> {
      commandList.add(VisualVmOptions.START_MEMORY_SAMPLER);
      commandList.add(String.valueOf(startMemorySamplerPid));
    });
    options.snapshotSamplerPid().ifPresent(snapshotSamplerPid -> {
      commandList.add(VisualVmOptions.SNAPSHOT_SAMPLER);
      commandList.add(String.valueOf(snapshotSamplerPid));
    });
    options.stopSamplerPid().ifPresent(stopSamplerPid -> {
      commandList.add(VisualVmOptions.STOP_SAMPLER);
      commandList.add(String.valueOf(stopSamplerPid));
    });
    options.windowToFront().ifPresent(windowToFront -> commandList.add(VisualVmOptions.WINDOW_TO_FRONT));
    return commandList;
  }
}
