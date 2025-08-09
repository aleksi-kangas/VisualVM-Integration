package com.github.aleksikangas.visualvm.integration.process;

import com.github.aleksikangas.visualvm.integration.options.VisualVmOptions;
import com.github.aleksikangas.visualvm.notifications.VisualVmNotifications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class VisualVmProcess implements Runnable {
  private static final String JVM_OPTIONS_PREFIX = "-J";

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
      VisualVmNotifications.notifyError(null, "Error starting VisualVM: " + e.getMessage());
    }
  }

  /**
   * Translates the {@link VisualVmProcess#options} to command line options.
   * @return The list of arguments to be passed to the process.
   */
  private List<String> buildCommandList() {
    final List<String> commandList = new ArrayList<>();
    // General
    commandList.add(options.executable());
    options.openPid().ifPresent(openPid -> {
      commandList.add(VisualVmCommandLineOptions.OPEN_PID.toString());
      commandList.add(String.valueOf(openPid));
    });
    options.openJmx().ifPresent(openJmx -> {
      commandList.add(VisualVmCommandLineOptions.OPEN_JMX.toString());
      commandList.add(openJmx);
    });
    options.openFile().ifPresent(openFile -> {
      commandList.add(VisualVmCommandLineOptions.OPEN_FILE.toString());
      commandList.add(openFile);
    });
    // Appearance
    options.fontSize().ifPresent(fontSize -> {
      commandList.add(VisualVmCommandLineOptions.FONT_SIZE.toString());
      commandList.add(String.valueOf(fontSize));
    });
    options.laf().ifPresent(laf -> {
      commandList.add(VisualVmCommandLineOptions.LAF.toString());
      commandList.add(laf.toString());
    });
    options.windowToFront()
           .ifPresent(windowToFront -> commandList.add(VisualVmCommandLineOptions.WINDOW_TO_FRONT.toString()));
    // JDK
    options.jdkHome().ifPresent(jdkHome -> {
      commandList.add(VisualVmCommandLineOptions.JDK_HOME.toString());
      commandList.add(jdkHome);
    });
    // JVM options
    options.jvmOptions().ifPresent(jvmOptions -> commandList.add(JVM_OPTIONS_PREFIX + jvmOptions));
    // Dumps
    options.heapDumpPid().ifPresent(heapDumpPid -> {
      commandList.add(VisualVmCommandLineOptions.HEAP_DUMP.toString());
      commandList.add(String.valueOf(heapDumpPid));
    });
    options.threadDumpPid().ifPresent(threadDumpPid -> {
      commandList.add(VisualVmCommandLineOptions.THREAD_DUMP.toString());
      commandList.add(String.valueOf(threadDumpPid));
    });
    // Sampler
    options.startCpuSamplerPid().ifPresent(startCpuSamplerPid -> {
      commandList.add(VisualVmCommandLineOptions.START_CPU_SAMPLER.toString());
      commandList.add(String.valueOf(startCpuSamplerPid));
    });
    options.startMemorySamplerPid().ifPresent(startMemorySamplerPid -> {
      commandList.add(VisualVmCommandLineOptions.START_MEMORY_SAMPLER.toString());
      commandList.add(String.valueOf(startMemorySamplerPid));
    });
    options.snapshotSamplerPid().ifPresent(snapshotSamplerPid -> {
      commandList.add(VisualVmCommandLineOptions.SNAPSHOT_SAMPLER.toString());
      commandList.add(String.valueOf(snapshotSamplerPid));
    });
    options.stopSamplerPid().ifPresent(stopSamplerPid -> {
      commandList.add(VisualVmCommandLineOptions.STOP_SAMPLER.toString());
      commandList.add(String.valueOf(stopSamplerPid));
    });
    // Sources
    options.sourceConfig().ifPresent(sourceConfig -> {
      commandList.add(VisualVmCommandLineOptions.SOURCE_CONFIG.toString());
      commandList.add(sourceConfig.getSourceConfigPath().toString());
    });
    options.sourceRoots().ifPresent(sourceRoots -> {
      commandList.add(VisualVmCommandLineOptions.SOURCE_ROOTS.toString());
      commandList.add(sourceRoots.toString());
    });
    options.sourceViewer().ifPresent(sourceViewer -> {
      commandList.add(VisualVmCommandLineOptions.SOURCE_VIEWER.toString());
      commandList.add(sourceViewer.toString());
    });
    // Directories
    options.userDir().ifPresent(userDir -> {
      commandList.add(VisualVmCommandLineOptions.USER_DIR.toString());
      commandList.add(userDir);
    });
    options.cacheDir().ifPresent(cacheDir -> {
      commandList.add(VisualVmCommandLineOptions.CACHE_DIR.toString());
      commandList.add(cacheDir);
    });
    // Miscellaneous
    options.prependClassPath()
           .ifPresent(prependClassPath -> Arrays
                   .stream(prependClassPath.classPaths())
                   .forEach(classPath -> {
                     commandList.add(VisualVmCommandLineOptions.CLASSPATH_PREPEND.toString());
                     commandList.add(classPath);
                   }));
    options.appendClassPath()
           .ifPresent(appendClassPath -> Arrays
                   .stream(appendClassPath.classPaths())
                   .forEach(classPath -> {
                     commandList.add(VisualVmCommandLineOptions.CLASSPATH_APPEND.toString());
                     commandList.add(classPath);
                   }));
    return commandList;
  }
}
