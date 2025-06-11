package com.github.aleksikangas.visualvm.process;

import com.github.aleksikangas.visualvm.notifications.VisualVmNotifications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ProcessUtils {
  private static final Pattern JPS_PROCESS_PATTERN = Pattern.compile("^(\\d+)\\s+(\\S+)");

  /**
   * List all java processes using <code>jps</code>.
   * @return List of {@link JpsProcess}es.
   */
  public static List<JpsProcess> listJpsProcesses() {
    final List<JpsProcess> jpsProcesses = new ArrayList<>();
    try {
      final Process jpsProcess = Runtime.getRuntime().exec(new String[]{"jps"});
      try (final var bufferedReader = new BufferedReader(new InputStreamReader(jpsProcess.getInputStream()))) {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          final Matcher matcher = JPS_PROCESS_PATTERN.matcher(line);
          if (matcher.find()) {
            try {
              jpsProcesses.add(new JpsProcess(Long.parseLong(matcher.group(1)), matcher.group(2)));
            } catch (final NumberFormatException e) {
              VisualVmNotifications.notifyWarning(null,
                                                  String.format("Error while parsing process (PID=%s, name=%s)",
                                                                matcher.group(1),
                                                                matcher.group(2)));
            }
          }
        }
      }
    } catch (final IOException e) {
      VisualVmNotifications.notifyError(null, "Error while attempting to list PIDs via jps");
    }
    return jpsProcesses;
  }

  private ProcessUtils() {
  }
}
