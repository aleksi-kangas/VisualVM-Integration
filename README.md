# VisualVM Integration

[![VisualVM Integration text](plugin-card.png)](https://plugins.jetbrains.com/plugin/27579-visualvm-integration)

## Settings

The plugin settings can be found in the `File` > `Settings` dialog under the `Tools` section.
**The path to the VisualVM executable is required.** The other options are optional. See
the [VisualVM Command Line Options](https://visualvm.github.io/docs/command-line-options.html) for more information.

![Settings](settings.png)

## Usage

The integration plugin offers a `Tools` > `VisualVM` menu item that can be used to launch VisualVM.

![ToolsMenu](tools-menu.png)

When an application is running, the `Run` menu bar offers the following VisualVM actions:

![RunMenuBar](run-menu-bar.png)

- Attach ![](src/main/resources/icons/visualvm_16.svg)
    - Attaching to the running process, i.e. opening the process view in VisualVM
- Thread Dump ![](src/main/resources/icons/visualvm_thread_dump_16_dark.svg)
    - Takes a thread dump and opens it in VisualVM
- Heap Dump ![](src/main/resources/icons/visualvm_heap_dump_16_dark.svg)
    - Takes a heap dump and opens it in VisualVM
- Start CPU Sampler ![](src/main/resources/icons/visualvm_start_cpu_sampler_16_dark.svg)
    - Starts CPU sampling
- Start Memory Sampler ![](src/main/resources/icons/visualvm_start_memory_sampler_16_dark.svg)
    - Starts memory sampling
- Sampler Snapshot ![](src/main/resources/icons/visualvm_snapshot_sampler_16_dark.svg)
    - Takes a sampler snapshot and opens it in VisualVM
- Stop Sampler ![](src/main/resources/icons/visualvm_stop_sampler_16_dark.svg)
    - Stops sampling

The actions may also be manually added to e.g. debugger menu bar, if so desired.
The only requirement is that the PID (process ID) is resolvable from the context.
Automatic PID selection may be turned off in the plugin settings,
and in such a case a popup menu for PID selection is displayed.

![ManualPIDSelection](manual-pid-selection.png)
