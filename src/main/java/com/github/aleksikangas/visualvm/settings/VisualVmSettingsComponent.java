package com.github.aleksikangas.visualvm.settings;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.VerticalFlowLayout;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

public final class VisualVmSettingsComponent {
  private final JPanel mainPanel = new JPanel();

  // VisualVM executable
  private final JBTextField executablePathField = new JBTextField();
  private final JButton executablePathBrowseButton = new JButton("Browse...");

  // JDK home
  private final JBCheckBox overrideJdkCheckBox = new JBCheckBox();
  private final JBLabel jdkHomeLabel = new JBLabel("JDK home:");
  private final JBTextField jdkHomePathTextField = new JBTextField();
  private final JButton jdkHomePathBrowseButton = new JButton("Browse...");

  // Appearance
  private final JBCheckBox windowToFrontCheckBox = new JBCheckBox();
  private final ComboBox<VisualVmLaf.Variant> lafComboBox = new ComboBox<>(VisualVmLaf.Variant.values());
  private final JBLabel customLafClassNameLabel = new JBLabel("Custom LAF:");
  private final JBTextField customLafClassNameTextField = new JBTextField();

  // Classpath
  private final JBTextField prependClassPathTextField = new JBTextField();
  private final JBTextField appendClassPathTextField = new JBTextField();

  public VisualVmSettingsComponent() {
    mainPanel.setLayout(new VerticalFlowLayout());

    final JPanel contentPanel = FormBuilder
            .createFormBuilder()
            .addLabeledComponent(new JBLabel("VisualVM executable:"), executablePathField)
            .addComponentToRightColumn(executablePathBrowseButton)
            .addSeparator()
            .addLabeledComponent(new JBLabel("Override JDK"), overrideJdkCheckBox)
            .addLabeledComponent(jdkHomeLabel, jdkHomePathTextField)
            .addComponentToRightColumn(jdkHomePathBrowseButton)
            .addSeparator()
            .addLabeledComponent(new JBLabel("Window to front:"), windowToFrontCheckBox)
            .addLabeledComponent(new JBLabel("Override LAF:"), lafComboBox)
            .addLabeledComponent(customLafClassNameLabel, customLafClassNameTextField)
            .addSeparator()
            .addLabeledComponent(new JBLabel("Prepend classpath:"), prependClassPathTextField)
            .addLabeledComponent(new JBLabel("Append classpath:"), appendClassPathTextField)
            .getPanel();
    mainPanel.add(contentPanel);

    initializeExecutablePath();
    initializeJdKHome();
    initializeAppearance();
    initializeClassPaths();
  }

  @NotNull
  public JPanel getPanel() {
    return mainPanel;
  }

  @NotNull
  public String getExecutablePath() {
    return executablePathField.getText();
  }

  public void setExecutablePath(@NotNull final String executablePath) {
    executablePathField.setText(Objects.requireNonNull(executablePath));
  }

  public boolean overrideJdk() {
    return overrideJdkCheckBox.isSelected();
  }

  public void setOverrideJdk(final boolean overrideJdk) {
    overrideJdkCheckBox.setSelected(overrideJdk);
    enableJdkOverrideComponents(overrideJdkCheckBox.isSelected());
  }

  @NotNull
  public String getJdkHome() {
    return jdkHomePathTextField.getText();
  }

  public void setJdkHome(final String jdkHome) {
    jdkHomePathTextField.setText(jdkHome);
  }

  public boolean windowToFront() {
    return windowToFrontCheckBox.isSelected();
  }

  public void setWindowToFront(final boolean windowToFront) {
    windowToFrontCheckBox.setSelected(windowToFront);
  }

  @NotNull
  public VisualVmLaf getLaf() {
    return switch ((VisualVmLaf.Variant) Objects.requireNonNull(lafComboBox.getSelectedItem())) {
      case NONE -> VisualVmLaf.NONE;
      case AQUA -> VisualVmLaf.AQUA;
      case GTK -> VisualVmLaf.GTK;
      case METAL -> VisualVmLaf.METAL;
      case WINDOWS -> VisualVmLaf.WINDOWS;
      case CUSTOM -> VisualVmLaf.custom(customLafClassNameTextField.getText());
    };
  }

  public void setLaf(@Nullable final VisualVmLaf laf) {
    if (laf != null) {
      customLafClassNameTextField.setText(laf.isCustom() ? laf.toString() : "");
      lafComboBox.setSelectedItem(laf.variant());
      enableCustomLafComponents(laf.isCustom());
    } else {
      customLafClassNameTextField.setText("");
      lafComboBox.setSelectedItem(VisualVmLaf.Variant.NONE);
      enableCustomLafComponents(false);
    }
  }

  public VisualVmClassPaths getPrependClassPath() {
    if (prependClassPathTextField.getText().isEmpty()) {
      return VisualVmClassPaths.EMPTY;
    }
    return VisualVmClassPaths.ofCommaSeparated(prependClassPathTextField.getText());
  }

  public void setPrependClassPath(@NotNull final VisualVmClassPaths prependClassPath) {
    prependClassPathTextField.setText(Objects.requireNonNull(prependClassPath).toString());
  }

  public VisualVmClassPaths getAppendClassPath() {
    if (appendClassPathTextField.getText().isEmpty()) {
      return VisualVmClassPaths.EMPTY;
    }
    return VisualVmClassPaths.ofCommaSeparated(appendClassPathTextField.getText());
  }

  public void setAppendClassPath(@NotNull final VisualVmClassPaths appendClassPath) {
    appendClassPathTextField.setText(Objects.requireNonNull(appendClassPath).toString());
  }

  private void browseExecutablePath() {
    final FileChooserDescriptor fileChooserDescriptor = FileChooserDescriptorFactory.singleFile();
    fileChooserDescriptor.setHideIgnored(true);
    final Project defaultProject = ProjectManager.getInstance().getDefaultProject();
    final VirtualFile preSelectedFile = LocalFileSystem.getInstance().findFileByPath(executablePathField.getText());
    final VirtualFile chosenFile = FileChooser.chooseFile(fileChooserDescriptor, defaultProject, preSelectedFile);
    if (chosenFile != null) {
      executablePathField.setText(chosenFile.getPath());
    }
  }

  private void enableJdkOverrideComponents(final boolean enabled) {
    jdkHomeLabel.setEnabled(enabled);
    jdkHomePathTextField.setEnabled(enabled);
    jdkHomePathBrowseButton.setEnabled(enabled);
  }

  private void browseJdkHomePath() {
    final FileChooserDescriptor fileChooserDescriptor = FileChooserDescriptorFactory.singleDir();
    fileChooserDescriptor.setHideIgnored(true);
    final Project defaultProject = ProjectManager.getInstance().getDefaultProject();
    final VirtualFile preSelectedFile = LocalFileSystem.getInstance().findFileByPath(jdkHomePathTextField.getText());
    final VirtualFile chosenFile = FileChooser.chooseFile(fileChooserDescriptor, defaultProject, preSelectedFile);
    if (chosenFile != null) {
      jdkHomePathTextField.setText(chosenFile.getPath());
    }
  }

  private void enableCustomLafComponents(final boolean enabled) {
    customLafClassNameLabel.setEnabled(enabled);
    customLafClassNameTextField.setEnabled(enabled);
  }

  private void initializeExecutablePath() {
    executablePathField.setToolTipText("Path to the VisualVM executable");
    executablePathBrowseButton.addActionListener(e -> browseExecutablePath());
  }

  private void initializeJdKHome() {
    overrideJdkCheckBox.addActionListener(e -> enableJdkOverrideComponents(overrideJdkCheckBox.isSelected()));
    enableJdkOverrideComponents(overrideJdkCheckBox.isSelected());
    jdkHomePathBrowseButton.addActionListener(e -> browseJdkHomePath());
    overrideJdkCheckBox.setToolTipText("(--jdkhome): Path to the JDK home");
    jdkHomePathTextField.setToolTipText("(--jdkhome): Path to the JDK home");
  }

  private void initializeAppearance() {
    windowToFrontCheckBox.setToolTipText("(--window-to-front): Brings VisualVM window to front if supported by the OS");
    lafComboBox.setToolTipText("(--laf): Override LAF (Look-and-Feel) with a predefined/custom LAF");
    lafComboBox.addActionListener(e -> {
      final VisualVmLaf.Variant lafVariant = (VisualVmLaf.Variant) Objects.requireNonNull(lafComboBox.getSelectedItem());
      enableCustomLafComponents(lafVariant == VisualVmLaf.Variant.CUSTOM);
    });
    customLafClassNameTextField.setToolTipText("Custom LAF class");
    enableCustomLafComponents(false);
  }

  private void initializeClassPaths() {
    prependClassPathTextField.setToolTipText(
            "(--cp:p): Prepend custom (comma-separated) classpath(s) to the VisualVM classpath");
    appendClassPathTextField.setToolTipText(
            "(--cp:a): Append custom (comma-separated) classpath(s) to the VisualVM classpath");
  }
}
