package com.github.aleksikangas.visualvm.integration.process;

enum VisualVmCommandLineOptions {
  // -------------------
  // Setup Options
  // -------------------
  /**
   * Sets the JDK installation to run VisualVM.
   */
  JDK_HOME("--jdkhome"),
  /**
   * Defines the directory to store user settings like remote hosts, JMX connections, etc., and installed plugins.
   */
  USER_DIR("--userdir"),
  /**
   * Defines the directory to store user cache, must be different from userdir.
   */
  CACHE_DIR("--cachedir"),
  /**
   * Prepends custom classpath to the VisualVM classpath.
   */
  CLASSPATH_PREPEND("--cp:p"),
  /**
   * Appends custom classpath to the VisualVM classpath.
   */
  CLASSPATH_APPEND("--cp:a"),

  // -------------------
  // Appearance Options
  // -------------------
  /**
   * Defines the Swing look and feel used to render the VisualVM GUI.
   */
  LAF("--laf"),
  /**
   * Defines the base font size used in the VisualVM GUI.
   */
  FONT_SIZE("--fontsize"),

  // -------------------
  // Functional Options
  // -------------------
  /**
   * Opens the provided file or snapshot in the VisualVM GUI, if supported (.tdump, .hprof, .nps, .npss, .jfr, .apps).
   */
  OPEN_FILE("--openfile"),
  /**
   * Opens a process with the provided id in the VisualVM GUI.
   */
  OPEN_ID("--openid"),
  /**
   * Opens a process with the provided pid in the VisualVM GUI.
   */
  OPEN_PID("--openpid"),
  /**
   * Opens a process specified by the provided JMX connection in the VisualVM GUI.
   */
  OPEN_JMX("--openjmx"),
  /**
   * Takes thread dump of the provided process and opens it in VisualVM GUI.
   */
  THREAD_DUMP("--threaddump"),
  /**
   * Takes heap dump of the provided process and opens it in VisualVM GUI.
   */
  HEAP_DUMP("--heapdump"),
  /**
   * Starts CPU sampler session for the provided process.
   */
  START_CPU_SAMPLER("--start-cpu-sampler"),
  /**
   * Starts memory sampler session for the provided process.
   */
  START_MEMORY_SAMPLER("--start-memory-sampler"),
  /**
   * Takes snapshot of the sampling data collected so far for the provided process.
   */
  SNAPSHOT_SAMPLER("--snapshot-sampler"),
  /**
   * Stops the sampler session of the provided process.
   */
  STOP_SAMPLER("--stop-sampler"),
  /**
   * Starts flight recording of the provided process.
   */
  START_JFR("--start-jfr"),
  /**
   * Dumps the flight recorder data collected so far for the provided process.
   */
  DUMP_JFR("--dump-jfr"),
  /**
   * Stops the flight recording of the provided process.
   */
  STOP_JFR("--stop-jfr"),
  /**
   * Defines the source roots to be searched for the sources by VisualVM.
   */
  SOURCE_ROOTS("--source-roots"),
  /**
   * Defines the command to launch an external sources viewer from VisualVM using Go to Source action.
   */
  SOURCE_VIEWER("--source-viewer"),
  /**
   * Defines --source-roots and --source-viewer in a single step using a properties file.
   * The file contains at least one of source-roots and source-viewer key/value pair in java.util.Properties format,
   * UTF-8 encoding.
   */
  SOURCE_CONFIG("--source-config"),
  /**
   * Brings the currently opened VisualVM window to front if supported by the OS window system.
   */
  WINDOW_TO_FRONT("--window-to-front");

  VisualVmCommandLineOptions(final String cliOption) {
    this.cliOption = cliOption;
  }

  private final String cliOption;

  @Override
  public String toString() {
    return cliOption;
  }
}
