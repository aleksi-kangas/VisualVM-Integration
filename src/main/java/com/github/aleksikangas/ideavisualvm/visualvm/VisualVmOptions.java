package com.github.aleksikangas.ideavisualvm.visualvm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * <a href="https://visualvm.github.io/docs/command-line-options.html">VisualVM Command Line Options</a>
 */
public record VisualVmOptions(String executable,
                              Optional<String> jdkHome,
                              Optional<String> openFile,
                              Optional<Long> openPid,
                              Optional<String> openJmx,
                              Optional<Long> threadDumpPid,
                              Optional<Long> heapDumpPid,
                              Optional<Long> startCpuSamplerPid,
                              Optional<Long> startMemorySamplerPid,
                              Optional<Long> snapshotSamplerPid,
                              Optional<Long> stopSamplerPid,
                              Optional<Boolean> windowToFront) {
  public static final class Builder {
    @NotNull
    private final String executable;
    @Nullable
    private String jdkHome = null;
    @Nullable
    private String openFile = null;
    @Nullable
    private Long openPid = null;
    @Nullable
    private String openJmx = null;
    @Nullable
    private Long threadDumpPid = null;
    @Nullable
    private Long heapDumpPid = null;
    @Nullable
    private Long startCpuSamplerPid = null;
    @Nullable
    private Long startMemorySamplerPid = null;
    @Nullable
    private Long snapshotSamplerPid = null;
    @Nullable
    private Long stopSamplerPid = null;
    @Nullable
    private Boolean windowToFront = null;

    public Builder(final @NotNull String executable) {
      this.executable = executable;
    }

    public VisualVmOptions build() {
      return new VisualVmOptions(this);
    }

    public Builder withJdkHome(@Nullable final String jdkHome) {
      this.jdkHome = jdkHome;
      return this;
    }

    public Builder withOpenFile(@Nullable final String openFile) {
      this.openFile = openFile;
      return this;
    }

    public Builder withOpenPid(@Nullable final Long openPid) {
      this.openPid = openPid;
      return this;
    }

    public Builder withOpenJmx(@Nullable final String openJmx) {
      this.openJmx = openJmx;
      return this;
    }

    public Builder withThreadDumpPid(@Nullable final Long threadDumpPid) {
      this.threadDumpPid = threadDumpPid;
      return this;
    }

    public Builder withHeapDumpPid(@Nullable final Long heapDumpPid) {
      this.heapDumpPid = heapDumpPid;
      return this;
    }

    public Builder withStartCpuSamplerPid(@Nullable final Long startCpuSamplerPid) {
      this.startCpuSamplerPid = startCpuSamplerPid;
      return this;
    }

    public Builder withStartMemorySamplerPid(@Nullable final Long startMemorySamplerPid) {
      this.startMemorySamplerPid = startMemorySamplerPid;
      return this;
    }

    public Builder withSnapshotSamplerPid(@Nullable final Long snapshotSamplerPid) {
      this.snapshotSamplerPid = snapshotSamplerPid;
      return this;
    }

    public Builder withStopSamplerPid(@Nullable final Long stopSamplerPid) {
      this.stopSamplerPid = stopSamplerPid;
      return this;
    }

    public Builder withWindowToFront(@Nullable final Boolean windowToFront) {
      this.windowToFront = windowToFront;
      return this;
    }
  }

  private VisualVmOptions(final Builder builder) {
    this(builder.executable,
         Optional.ofNullable(builder.jdkHome),
         Optional.ofNullable(builder.openFile),
         Optional.ofNullable(builder.openPid),
         Optional.ofNullable(builder.openJmx),
         Optional.ofNullable(builder.threadDumpPid),
         Optional.ofNullable(builder.heapDumpPid),
         Optional.ofNullable(builder.startCpuSamplerPid),
         Optional.ofNullable(builder.startMemorySamplerPid),
         Optional.ofNullable(builder.snapshotSamplerPid),
         Optional.ofNullable(builder.stopSamplerPid),
         Optional.ofNullable(builder.windowToFront));
  }

  // -------------------
  // Setup Options
  // -------------------
  /**
   * Sets the JDK installation to run VisualVM.
   */
  static final String JDK_HOME = "--jdkhome";
  /**
   * Defines the directory to store user settings like remote hosts, JMX connections, etc., and installed plugins.
   */
  static final String USER_DIR = "--userdir";
  /**
   * Defines the directory to store user cache, must be different from userdir.
   */
  static final String CACHE_DIR = "--cachedir";
  /**
   * Prepends custom classpath to the VisualVM classpath.
   */
  static final String CP_P = "--cp:p";
  /**
   * Appends custom classpath to the VisualVM classpath.
   */
  static final String CP_A = "--cp:a";

  // -------------------
  // Appearance Options
  // -------------------
  /**
   * Defines the Swing look and feel used to render the VisualVM GUI.
   */
  static final String LAF = "--laf";
  /**
   * Defines the base font size used in the VisualVM GUI.
   */
  static final String FONT_SIZE = "--fontsize";

  // -------------------
  // Functional Options
  // -------------------
  /**
   * Opens the provided file or snapshot in the VisualVM GUI, if supported (.tdump, .hprof, .nps, .npss, .jfr, .apps).
   */
  static final String OPEN_FILE = "--openfile";
  /**
   * Opens a process with the provided id in the VisualVM GUI.
   */
  static final String OPEN_ID = "--openid";
  /**
   * Opens a process with the provided pid in the VisualVM GUI.
   */
  static final String OPEN_PID = "--openpid";
  /**
   * Opens a process specified by the provided JMX connection in the VisualVM GUI.
   */
  static final String OPEN_JMX = "--openjmx";
  /**
   * Takes thread dump of the provided process and opens it in VisualVM GUI.
   */
  static final String THREAD_DUMP = "--threaddump";
  /**
   * Takes heap dump of the provided process and opens it in VisualVM GUI.
   */
  static final String HEAP_DUMP = "--heapdump";
  /**
   * Starts CPU sampler session for the provided process.
   */
  static final String START_CPU_SAMPLER = "--start-cpu-sampler";
  /**
   * Starts memory sampler session for the provided process.
   */
  static final String START_MEMORY_SAMPLER = "--start-memory-sampler";
  /**
   * Takes snapshot of the sampling data collected so far for the provided process.
   */
  static final String SNAPSHOT_SAMPLER = "--snapshot-sampler";
  /**
   * Stops the sampler session of the provided process.
   */
  static final String STOP_SAMPLER = "--stop-sampler";
  /**
   * Starts flight recording of the provided process.
   */
  static final String START_JFR = "--start-jfr";
  /**
   * Dumps the flight recorder data collected so far for the provided process.
   */
  static final String DUMP_JFR = "--dump-jfr";
  /**
   * Stops the flight recording of the provided process.
   */
  static final String STOP_JFR = "--stop-jfr";
  /**
   * Defines the source roots to be searched for the sources by VisualVM.
   */
  static final String SOURCE_ROOTS = "--source-roots";
  /**
   * Defines the command to launch an external sources viewer from VisualVM using Go to Source action.
   */
  static final String SOURCE_VIEWER = "--source-viewer";
  /**
   * Defines --source-roots and --source-viewer in a single step using a properties file.
   * The file contains at least one of source-roots and source-viewer key/value pair in java.util.Properties format,
   * UTF-8 encoding.
   */
  static final String SOURCE_CONFIG = "--source-config";
  /**
   * Brings the currently opened VisualVM window to front if supported by the OS window system.
   */
  static final String WINDOW_TO_FRONT = "--window-to-front";
}
