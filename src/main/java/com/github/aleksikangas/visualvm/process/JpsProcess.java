package com.github.aleksikangas.visualvm.process;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A wrapper for a process listed with <code>jps</code>.
 * @param pid  Process ID
 * @param name Process name
 */
public record JpsProcess(long pid,
                         String name) {
  @Override
  public boolean equals(final Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    final JpsProcess that = (JpsProcess) o;
    return pid == that.pid && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pid, name);
  }

  @Override
  public @NotNull String toString() {
    return pid + " " + name;
  }
}
