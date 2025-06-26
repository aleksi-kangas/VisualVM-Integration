package com.github.aleksikangas.visualvm.integration.options;

import com.github.aleksikangas.visualvm.integration.options.appearance.VisualVmLaf;
import com.github.aleksikangas.visualvm.integration.options.misc.VisualVmClassPaths;
import com.github.aleksikangas.visualvm.integration.options.sources.VisualVmSourceConfig;
import com.github.aleksikangas.visualvm.integration.options.sources.VisualVmSourceRoots;
import com.github.aleksikangas.visualvm.integration.options.sources.VisualVmSourceViewer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * <a href="https://visualvm.github.io/docs/command-line-options.html">VisualVM Command Line Options</a>
 */
public record VisualVmOptions(
        // General
        String executable,
        Optional<Long> openPid,
        Optional<String> openJmx,
        Optional<String> openFile,

        // Appearance
        Optional<Integer> fontSize,
        Optional<VisualVmLaf> laf,
        Optional<Boolean> windowToFront,

        // JDK
        Optional<String> jdkHome,

        // Dumps
        Optional<Long> heapDumpPid,
        Optional<Long> threadDumpPid,

        // Sampler
        Optional<Long> startCpuSamplerPid,
        Optional<Long> startMemorySamplerPid,
        Optional<Long> snapshotSamplerPid,
        Optional<Long> stopSamplerPid,

        // Sources
        Optional<VisualVmSourceConfig> sourceConfig,
        Optional<VisualVmSourceRoots> sourceRoots,
        Optional<VisualVmSourceViewer> sourceViewer,

        // Directories
        Optional<String> userDir,
        Optional<String> cacheDir,

        // Miscellaneous
        Optional<VisualVmClassPaths> prependClassPath,
        Optional<VisualVmClassPaths> appendClassPath) {

  public static final class Builder {
    // General
    @NotNull
    private final String executable;
    @Nullable
    private Long openPid = null;
    @Nullable
    private String openJmx = null;
    @Nullable
    private String openFile = null;

    // Appearance
    @Nullable
    private Integer fontSize = null;
    @Nullable
    private VisualVmLaf laf = null;
    @Nullable
    private Boolean windowToFront = null;

    // JDK
    @Nullable
    private String jdkHome = null;

    // Dumps
    @Nullable
    private Long heapDumpPid = null;
    @Nullable
    private Long threadDumpPid = null;

    // Sampler
    @Nullable
    private Long startCpuSamplerPid = null;
    @Nullable
    private Long startMemorySamplerPid = null;
    @Nullable
    private Long snapshotSamplerPid = null;
    @Nullable
    private Long stopSamplerPid = null;

    // Sources
    @Nullable
    private VisualVmSourceConfig sourceConfig = null;
    @Nullable
    private VisualVmSourceRoots sourceRoots = null;
    @Nullable
    private VisualVmSourceViewer sourceViewer = null;

    // Directories
    @Nullable
    private String userDir = null;
    @Nullable
    private String cacheDir = null;

    // Miscellaneous
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

    public Builder withOpenPid(@Nullable final Long openPid) {
      this.openPid = openPid;
      return this;
    }

    public Builder withOpenJmx(@Nullable final String openJmx) {
      this.openJmx = openJmx;
      return this;
    }

    public Builder withOpenFile(@Nullable final String openFile) {
      this.openFile = openFile;
      return this;
    }

    public Builder withFontSize(@Nullable final Integer fontSize) {
      this.fontSize = fontSize;
      return this;
    }

    public Builder withLaf(@Nullable final VisualVmLaf laf) {
      this.laf = laf;
      return this;
    }

    public Builder withWindowToFront(@Nullable final Boolean windowToFront) {
      this.windowToFront = windowToFront;
      return this;
    }

    public Builder withJdkHome(@Nullable final String jdkHome) {
      this.jdkHome = jdkHome;
      return this;
    }

    public Builder withHeapDumpPid(@Nullable final Long heapDumpPid) {
      this.heapDumpPid = heapDumpPid;
      return this;
    }

    public Builder withThreadDumpPid(@Nullable final Long threadDumpPid) {
      this.threadDumpPid = threadDumpPid;
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

    public Builder withSourceConfig(@Nullable final VisualVmSourceConfig sourceConfig) {
      this.sourceConfig = sourceConfig;
      return this;
    }

    public Builder withSourceRoots(@Nullable final VisualVmSourceRoots sourceRoots) {
      this.sourceRoots = sourceRoots;
      return this;
    }

    public Builder withSourceViewer(@Nullable final VisualVmSourceViewer sourceViewer) {
      this.sourceViewer = sourceViewer;
      return this;
    }

    public Builder withUserDir(@Nullable final String userDir) {
      this.userDir = userDir;
      return this;
    }

    public Builder withCacheDir(@Nullable final String cacheDir) {
      this.cacheDir = cacheDir;
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
         Optional.ofNullable(builder.openPid),
         Optional.ofNullable(builder.openJmx),
         Optional.ofNullable(builder.openFile),
         Optional.ofNullable(builder.fontSize),
         Optional.ofNullable(builder.laf),
         Optional.ofNullable(builder.windowToFront),
         Optional.ofNullable(builder.jdkHome),
         Optional.ofNullable(builder.heapDumpPid),
         Optional.ofNullable(builder.threadDumpPid),
         Optional.ofNullable(builder.startCpuSamplerPid),
         Optional.ofNullable(builder.startMemorySamplerPid),
         Optional.ofNullable(builder.snapshotSamplerPid),
         Optional.ofNullable(builder.stopSamplerPid),
         Optional.ofNullable(builder.sourceConfig),
         Optional.ofNullable(builder.sourceRoots),
         Optional.ofNullable(builder.sourceViewer),
         Optional.ofNullable(builder.userDir),
         Optional.ofNullable(builder.cacheDir),
         Optional.ofNullable(builder.prependClassPath),
         Optional.ofNullable(builder.appendClassPath));
  }
}
