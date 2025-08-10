package com.github.aleksikangas.visualvm.process;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record JvmProcess(long pid,
                         String name) {
  @Override
  public boolean equals(final Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    final JvmProcess that = (JvmProcess) o;
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
