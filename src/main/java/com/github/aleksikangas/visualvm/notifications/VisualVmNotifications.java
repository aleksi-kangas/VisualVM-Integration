package com.github.aleksikangas.visualvm.notifications;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public final class VisualVmNotifications {
  public static void notifyError(@Nullable final Project project, final String content) {
    notification(project, content, NotificationType.ERROR);
  }

  public static void notifyWarning(@Nullable final Project project, final String content) {
    notification(project, content, NotificationType.WARNING);
  }

  public static void notifyInfo(@Nullable final Project project, final String content) {
    notification(project, content, NotificationType.INFORMATION);
  }

  public static void notification(final Project project,
                                  final String content,
                                  final NotificationType notificationType) {
    NotificationGroupManager.getInstance()
                            .getNotificationGroup("com.github.aleksikangas.visualvm.notifications")
                            .createNotification("VisualVM integration", content, notificationType)
                            .notify(project);
  }
}
