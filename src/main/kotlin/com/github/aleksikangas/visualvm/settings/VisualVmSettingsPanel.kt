package com.github.aleksikangas.visualvm.settings

import com.github.aleksikangas.visualvm.integration.options.appearance.CustomVisualVmLaf
import com.github.aleksikangas.visualvm.integration.options.appearance.DefaultVisualVmLaf
import com.github.aleksikangas.visualvm.integration.options.appearance.VisualVmLaf
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*
import fleet.util.cast
import io.ktor.util.reflect.*

data class VisualVmSettingsPanel(val panel: DialogPanel, val model: VisualVmSettingsPanelModel)

fun create(state: VisualVmSettings.State): VisualVmSettingsPanel {
    lateinit var panel: DialogPanel
    val model = VisualVmSettingsPanelModel.from(state)
    panel = panel {
        group("General") {
            row("VisualVM executable:") {
                textFieldWithBrowseButton(FileChooserDescriptorFactory.singleFile(), null, null)
                    .align(AlignX.FILL)
                    .bindText(model::executablePath)
                    .bold()
            }
            row {
                checkBox("IDE as source viewer")
                    .bindSelected(model::ideAsSourceViewer)
                    .comment("(--source-viewer): Defines the current IDE as the external sources viewer in VisualVM.")
            }
            row {
                checkBox("Automatic PID selection")
                    .bindSelected(model::automaticPid)
                    .comment("Automatically attempts to resolve the PID of the running process. Disable if wanting to target the process manually (e.g. a child process).")
            }
            row {
                checkBox("Automatic source roots")
                    .bindSelected(model::automaticSourceRoots)
                    .comment("(--source-roots): Automatically defines the source roots to be searched for the sources by VisualVM.")
            }
        }
        group("Appearance") {
            row("Font size") {
                spinner(1..100)
                    .bindIntValue(model::fontSize)
                    .comment("(--font-size): Defines the base font size used in the VisualVM GUI.")
            }
            row {
                checkBox("Window to front")
                    .bindSelected(model::windowToFront)
                    .comment("(--window-to-front): Brings the currently opened VisualVM window to front if supported by the OS window system.")
            }
            row("Override LAF:") {
                comboBox(DefaultVisualVmLaf.Variant.entries)
                    .bindItem(model::defaultVisualVmLaf.toNullableProperty())
                    .comment("(--laf): Defines the Swing look and feel used to render the VisualVM GUI.")
            }
            row("Custom LAF:") {
                textField()
                    .align(AlignX.FILL)
                    .bindText(model::customLafClassName)
                    .comment("(--laf): Overrides the Swing look and feel with a custom LAF.")
            }
        }
        group("JDK") {
            row("JDK home:") {
                textFieldWithBrowseButton(FileChooserDescriptorFactory.singleDir(), null, null)
                    .align(AlignX.FILL)
                    .bindText(model::jdkHomePath)
                    .comment("(--jdkhome): Overrides the JDK installation used with VisualVM.")
            }
        }
        group("Directories") {
            row("Cache directory:") {
                textFieldWithBrowseButton(FileChooserDescriptorFactory.singleDir(), null, null)
                    .align(AlignX.FILL)
                    .bindText(model::cacheDirPath)
                    .comment("(--cachedir): Defines the directory to store user cache, must be different from user directory.")
            }
            row("User directory:") {
                textFieldWithBrowseButton(FileChooserDescriptorFactory.singleDir(), null, null)
                    .align(AlignX.FILL)
                    .bindText(model::userDirPath)
                    .comment("(--userdir): Defines the directory to store user settings like remote hosts, JMX connections, etc., and installed plugins. Cannot be within the VisualVM installation directory.")
            }
        }
        group("Miscellaneous") {
            row("Prepend classpath:") {
                textField()
                    .align(AlignX.FILL)
                    .bindText(model::prependClassPath)
                    .comment("(--cp-p): Prepends custom classpath to the VisualVM classpath, separated by comma (',').")
            }
            row("Append classpath:") {
                textField()
                    .align(AlignX.FILL)
                    .bindText(model::appendClassPath)
                    .comment("(--cp-a): Appends custom classpath to the VisualVM classpath, separated by comma (',').")
            }
        }
    }
    return VisualVmSettingsPanel(panel, model)
}

data class VisualVmSettingsPanelModel(
    // General
    var executablePath: String,
    var ideAsSourceViewer: Boolean,
    var automaticSourceRoots: Boolean,
    var automaticPid: Boolean,
    // Appearance
    var fontSize: Int,
    var windowToFront: Boolean,
    var defaultVisualVmLaf: DefaultVisualVmLaf.Variant,
    var customLafClassName: String,
    // JDK
    var jdkHomePath: String,
    // Directories
    var cacheDirPath: String,
    var userDirPath: String,
    // Miscellaneous
    var prependClassPath: String,
    var appendClassPath: String
) {
    companion object {
        fun from(state: VisualVmSettings.State): VisualVmSettingsPanelModel {
            return VisualVmSettingsPanelModel(
                state.executablePath().orElse(""),
                state.ideAsSourceViewer(),
                state.automaticSourceRoots(),
                state.automaticPid(),
                state.fontSize(),
                state.windowToFront(),
                state
                    .laf()
                    .filter { x -> x.instanceOf(DefaultVisualVmLaf::class) }
                    .map { x -> x.cast<DefaultVisualVmLaf>() }
                    .map { x -> x.variant }
                    .orElse(DefaultVisualVmLaf.Variant.NONE),
                state
                    .laf()
                    .filter { x -> x.instanceOf(CustomVisualVmLaf::class) }
                    .map { x -> x.cast<CustomVisualVmLaf>() }
                    .map { x -> x.value() }
                    .orElse(""),
                state.jdkHomePath().orElse(""),
                state.cacheDirPath().orElse(""),
                state.userDirPath().orElse(""),
                state.prependClassPath().map { it.toString() }.orElse(""),
                state.appendClassPath().map { it.toString() }.orElse("")
            )
        }
    }

    fun getLaf(): VisualVmLaf {
        if (customLafClassName.isNotBlank()) return CustomVisualVmLaf(customLafClassName)
        return DefaultVisualVmLaf(defaultVisualVmLaf)
    }
}