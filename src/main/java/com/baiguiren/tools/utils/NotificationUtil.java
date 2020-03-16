package com.baiguiren.tools.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.ProjectManager;

public class NotificationUtil {
    public static void notification(String msg) {
        Notification notification = new Notification("tw tools", "Notify", msg, NotificationType.INFORMATION);
        notification.notify(ProjectManager.getInstance().getOpenProjects()[0]);
    }
}
