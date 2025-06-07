package com.github.aleksikangas.visualvm.settings;

public enum VisualVmLaf {
  METAL("Metal"),
  WINDOWS("Windows"),
  AQUA("Aqua"),
  GTK("GTK");

  private final String value;

  VisualVmLaf(final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
