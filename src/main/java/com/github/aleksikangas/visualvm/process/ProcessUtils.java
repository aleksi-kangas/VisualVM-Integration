package com.github.aleksikangas.visualvm.process;

import com.github.aleksikangas.visualvm.notifications.VisualVmNotifications;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.ArrayList;
import java.util.List;

public final class ProcessUtils {
  public static List<JvmProcess> listJvmProcesses() {
    final List<JvmProcess> jvmProcesses = new ArrayList<>();
    for (final VirtualMachineDescriptor virtualMachineDescriptor : VirtualMachine.list()) {
      try {
        jvmProcesses.add(new JvmProcess(Long.parseLong(virtualMachineDescriptor.id()),
                                        virtualMachineDescriptor.displayName().split("\\s+")[0]));
      } catch (final NumberFormatException e) {
        VisualVmNotifications.notifyWarning(null,
                                            String.format("Error while parsing process (PID=%s, name=%s)",
                                                          virtualMachineDescriptor.id(),
                                                          virtualMachineDescriptor.displayName()));
      }
    }
    return jvmProcesses;
  }

  private ProcessUtils() {
  }
}
