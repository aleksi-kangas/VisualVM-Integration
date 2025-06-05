package com.github.aleksikangas.ideavisualvm.settings;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

public final class VisualVmSettingsComponent {
  private final JPanel mainPanel;

  private final JBTextField executablePathField = new JBTextField();
  private final JButton executablePathBrowseButton = new JButton("Browse");

  private final JBCheckBox overrideJdkCheckBox = new JBCheckBox();
  private final JBLabel jdkHomeLabel = new JBLabel("JDK home:");
  private final JBTextField jdkHomePathTextField = new JBTextField();
  private final JButton jdkHomePathBrowseButton = new JButton("Browse");

  public VisualVmSettingsComponent() {
    mainPanel = FormBuilder.createFormBuilder()
                           .addLabeledComponent(new JBLabel("VisualVM executable:"), executablePathField)
                           .addComponentToRightColumn(executablePathBrowseButton)
                           .addLabeledComponent(new JBLabel("Override JDK"), overrideJdkCheckBox)
                           .addLabeledComponent(jdkHomeLabel, jdkHomePathTextField)
                           .addComponentToRightColumn(jdkHomePathBrowseButton)
                           .getPanel();

    executablePathBrowseButton.addActionListener(e -> browseExecutablePath());

    overrideJdkCheckBox.addActionListener(e -> {
      enableJdkOverrideComponents(overrideJdkCheckBox.isSelected());
    });
    enableJdkOverrideComponents(overrideJdkCheckBox.isSelected());
  }

  public JPanel getPanel() {
    return mainPanel;
  }

  public String getExecutablePath() {
    return executablePathField.getText();
  }

  public void setExecutablePath(final String executablePath) {
    executablePathField.setText(executablePath);
  }

  public boolean overrideJdk() {
    return overrideJdkCheckBox.isSelected();
  }

  public void setOverrideJdk(final boolean overrideJdk) {
    overrideJdkCheckBox.setSelected(overrideJdk);
    enableJdkOverrideComponents(overrideJdkCheckBox.isSelected());
  }

  public String getJdkHome() {
    return jdkHomePathTextField.getText();
  }

  public void setJdkHome(final String jdkHome) {
    jdkHomePathTextField.setText(jdkHome);
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
}
