package com.github.aleksikangas.visualvm.settings

import com.github.aleksikangas.visualvm.integration.options.appearance.VisualVmLaf
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.*

data class VisualVmSettingsPanel(val panel: DialogPanel, val model: VisualVmSettingsPanelModel)

fun create(state: VisualVmSettings.State): VisualVmSettingsPanel {
    lateinit var panel: DialogPanel
    val model = VisualVmSettingsPanelModel(
        state.executablePath,
        state.ideAsSourceViewer,
        state.automaticPid,
        state.automaticSourceRoots,
        state.windowToFront,
        state.laf.overrideVariant(),
        if (state.laf.overrideVariant() === VisualVmLaf.OverrideVariant.CUSTOM) state.laf.toString() else "",
        state.overrideJdk,
        state.jdkHomePath,
        state.prependClassPath.toString(),
        state.appendClassPath.toString()
    )
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
                    .comment("Automatically attempts to resolve the PID of the running process. Disable if wanting to target e.g. a child process.")
            }
            row {
                checkBox("Automatic source roots")
                    .bindSelected(model::automaticSourceRoots)
                    .comment("(--source-roots): Automatically defines the source roots to be searched for the sources by VisualVM.")
            }
        }
        group("Appearance") {
            row {
                checkBox("Window to front")
                    .bindSelected(model::windowToFront)
                    .comment("(--window-to-front): Brings the currently opened VisualVM window to front if supported by the OS window system.")
            }
            row("Override LAF:") {
                comboBox(VisualVmLaf.OverrideVariant.entries)
                    .bindItem(model::overrideLaf.toNullableProperty())
                    .comment("(--laf): Defines the Swing look and feel used to render the VisualVM GUI.")
            }
            row("Custom LAF:") {
                textField()
                    .bindText(model::customLafClassName)
            }
        }
        group("JDK") {
            lateinit var overrideJdkCheckBox: Cell<JBCheckBox>
            row {
                overrideJdkCheckBox = checkBox("Override JDK")
                    .bindSelected(model::overrideJdk)
                    .comment("(--jdkhome): Overrides the JDK installation used with VisualVM.")
            }
            row("JDK home:") {
                textFieldWithBrowseButton(FileChooserDescriptorFactory.singleDir(), null, null)
                    .align(AlignX.FILL)
                    .bindText(model::jdkHomePath)
                    .enabledIf(overrideJdkCheckBox.selected)
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
    var automaticPid: Boolean,
    var automaticSourceRoots: Boolean,
    // Appearance
    var windowToFront: Boolean,
    var overrideLaf: VisualVmLaf.OverrideVariant,
    var customLafClassName: String,
    // JDK
    var overrideJdk: Boolean,
    var jdkHomePath: String,
    // Miscellaneous
    var prependClassPath: String,
    var appendClassPath: String
) {
    fun getLaf(): VisualVmLaf {
        return when (overrideLaf) {
            VisualVmLaf.OverrideVariant.NONE -> VisualVmLaf.NONE
            VisualVmLaf.OverrideVariant.AQUA -> VisualVmLaf.AQUA
            VisualVmLaf.OverrideVariant.GTK -> VisualVmLaf.GTK
            VisualVmLaf.OverrideVariant.METAL -> VisualVmLaf.METAL
            VisualVmLaf.OverrideVariant.WINDOWS -> VisualVmLaf.WINDOWS
            VisualVmLaf.OverrideVariant.CUSTOM -> VisualVmLaf.custom(customLafClassName)
        }
    }
}