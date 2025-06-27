package com.github.aleksikangas.visualvm.integration.options.sources;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public final class VisualVmSourceUtils {
  /**
   * Encodes the given value by replacing:
   * <ul>
   *   <li>spaces with '%20'</li>
   *   <li>single quotes with '%27'</li>
   *   <item>double quotes with '%22'</item>
   * </ul>
   * @param value to encode
   * @return encoded value
   */
  public static String encodeValue(@NotNull final String value) {
    return value.replace(" ", "%20")
                .replace("'", "%27")
                .replace("\"", "%22");
  }

  /**
   * Resolve the source roots for a given module.
   * @param module the module to resolve the source roots for
   * @return the resolved source roots
   * @apiNote The resolved source roots follow the convention <code>root[subpaths=subpath1:subpath2]</code>.
   */
  public static Set<String> resolveModuleSourceRoots(@NotNull final Module module) {
    final var moduleRootManager = ModuleRootManager.getInstance(module);
    final var sourceRootSubPaths = new SourceRootSubPaths();
    for (final ContentEntry contentEntry : moduleRootManager.getContentEntries()) {
      final var contentEntryRoot = contentEntry.getFile();
      if (contentEntryRoot == null) continue;
      Arrays.stream(contentEntry.getSourceFolders())
            .filter(sourceFolder -> !sourceFolder.isTestSource())
            .map(SourceFolder::getFile)
            .filter(Objects::nonNull)
            .forEach(sourceRootPath -> {
              sourceRootSubPaths.add(contentEntryRoot.getPath(),
                                     VfsUtilCore.getRelativePath(sourceRootPath, contentEntryRoot));
            });
    }
    return sourceRootSubPaths.combine();
  }

  /**
   * Resolve the library source roots for a given module.
   * @param module the module to resolve the source roots for
   * @return the resolved source roots
   * @apiNote The resolved source roots follow the convention <code>root[subpaths=subpath1:subpath2]</code>.
   */
  public static Set<String> resolveModuleLibrarySourceRoots(@NotNull final Module module) {
    final var moduleRootManager = ModuleRootManager.getInstance(module);
    final var librarySubPaths = resolveArchiveSourceRootSubPaths(moduleRootManager.orderEntries()
                                                                                  .librariesOnly()
                                                                                  .sources()
                                                                                  .usingCache()
                                                                                  .getRoots());
    return librarySubPaths.combine();
  }

  /**
   * Resolve the SDK source roots for a given module.
   * @param module the module to resolve the source roots for
   * @return the resolved source roots
   * @apiNote The resolved source roots follow the convention <code>root[subpaths=subpath1:subpath2]</code>.
   */
  public static Set<String> resolveSdkSourceRoots(@NotNull final Module module) {
    final var moduleRootManager = ModuleRootManager.getInstance(module);
    final var sdkSubPaths = resolveArchiveSourceRootSubPaths(moduleRootManager.orderEntries()
                                                                              .sdkOnly()
                                                                              .sources()
                                                                              .usingCache()
                                                                              .getRoots());
    return sdkSubPaths.combine();
  }

  private static SourceRootSubPaths resolveArchiveSourceRootSubPaths(@NotNull final VirtualFile[] sourceRoots) {
    final var ARCHIVE_SUBPATH_SEPARATOR = "!/";
    final var archiveSubPaths = new SourceRootSubPaths();
    Arrays.stream(sourceRoots)
          .map(VirtualFile::getPath)
          .forEach(sourceRootPath -> {
            if (sourceRootPath.contains(ARCHIVE_SUBPATH_SEPARATOR)) {
              final var archive = StringUtil.substringBefore(sourceRootPath, ARCHIVE_SUBPATH_SEPARATOR);
              final var subPath = StringUtil.substringAfter(sourceRootPath, ARCHIVE_SUBPATH_SEPARATOR);
              archiveSubPaths.add(archive, subPath);
            } else {
              archiveSubPaths.add(sourceRootPath);
            }
          });
    return archiveSubPaths;
  }

  private static final class SourceRootSubPaths {
    private final Map<String, Set<String>> sourceRootSubPaths = new HashMap<>();

    public void add(@Nullable final String sourceRoot) {
      if (sourceRoot == null) return;
      sourceRootSubPaths.computeIfAbsent(sourceRoot, k -> new HashSet<>());
    }

    public void add(@Nullable final String sourceRoot, @Nullable final String subPath) {
      if (sourceRoot == null) return;
      if (subPath == null || subPath.isBlank()) {
        sourceRootSubPaths.computeIfAbsent(sourceRoot, k -> new HashSet<>());
      } else {
        sourceRootSubPaths.computeIfAbsent(sourceRoot, k -> new HashSet<>()).add(subPath);
      }
    }

    public Set<String> combine() {
      return sourceRootSubPaths.entrySet()
                               .stream()
                               .map(e -> {
                                 final var sourceRoot = e.getKey();
                                 final var subPaths = e.getValue();
                                 if (subPaths.isEmpty()) {
                                   return sourceRoot;
                                 } else {
                                   return String.format("%s[subpaths=%s]", sourceRoot, String.join(":", subPaths));
                                 }
                               })
                               .collect(Collectors.toSet());
    }
  }

  private VisualVmSourceUtils() {
  }
}
