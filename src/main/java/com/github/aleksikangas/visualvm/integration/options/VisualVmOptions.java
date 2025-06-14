package com.github.aleksikangas.visualvm.integration.options;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * <a href="https://visualvm.github.io/docs/command-line-options.html">VisualVM Command Line Options</a>
 */
public record VisualVmOptions(String executable,
                              Optional<VisualVmSourceViewer> sourceViewer,
                              Optional<VisualVmSourceRoots> sourceRoots,
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
                              Optional<Boolean> windowToFront,
                              Optional<VisualVmLaf> laf,
                              Optional<VisualVmClassPaths> prependClassPath,
                              Optional<VisualVmClassPaths> appendClassPath) {
  public static final class Builder {
    @NotNull
    private final String executable;
    @Nullable
    private VisualVmSourceViewer sourceViewer = null;
    @Nullable
    private VisualVmSourceRoots sourceRoots = null;
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
    @Nullable
    private VisualVmLaf laf = null;
    @Nullable
    private VisualVmClassPaths prependClassPath = null;
    @Nullable
    private VisualVmClassPaths appendClassPath = null;

    public Builder(final @NotNull String executable) {
      this.executable = executable;
    }

    public VisualVmOptions build() {
      return new VisualVmOptions(this);
    }

    public Builder withSourceViewer(@Nullable final VisualVmSourceViewer sourceViewer) {
      this.sourceViewer = sourceViewer;
      return this;
    }

    public Builder withSourceRoots(@Nullable final VisualVmSourceRoots sourceRoots) {
      this.sourceRoots = sourceRoots;
      return this;
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

    public Builder withLaf(@Nullable final VisualVmLaf laf) {
      this.laf = laf;
      return this;
    }

    public Builder withPrependClassPath(@Nullable final VisualVmClassPaths prependClassPath) {
      this.prependClassPath = prependClassPath;
      return this;
    }

    public Builder withAppendClassPath(@Nullable final VisualVmClassPaths appendClassPath) {
      this.appendClassPath = appendClassPath;
      return this;
    }
  }

  private VisualVmOptions(final Builder builder) {
    this(builder.executable,
         Optional.ofNullable(builder.sourceViewer),
         Optional.ofNullable(builder.sourceRoots),
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
         Optional.ofNullable(builder.windowToFront),
         Optional.ofNullable(builder.laf),
         Optional.ofNullable(builder.prependClassPath),
         Optional.ofNullable(builder.appendClassPath));
  }
}
