package com.baiguiren.tools.env;

import com.baiguiren.tools.utils.ProjectUtil;
import com.intellij.ide.SaveAndSyncHandlerImpl;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public class SwitchEnv {

    private static String env;

    private static HashMap<String, String> domains = new HashMap<>();

    private static HashMap<String, String> files = new HashMap<>();

    private static void init(String env) {
        SwitchEnv.env = env;

        putDomains();
        putFiles();
    }

    private static void putDomains() {
        domains.put("qa1", "qa1.api.86yqy.com");
        domains.put("qa2", "qa2.api.86yqy.com");
        domains.put("qa3", "qa3.api.86yqy.com");
        domains.put("release", "demo.api.86yqy.com");
    }

    private static void putFiles() {
        files.put("qa1", ".env.dev.qa1");
        files.put("qa2", ".env.dev.qa2");
        files.put("qa3", ".env.dev.qa3");
        files.put("release", ".env.dev.release");
    }

    public static void switchToEnv(AnActionEvent e, String env) throws IOException {
        Project project = ProjectUtil.getActiveProject();
        project.save();
        FileDocumentManager.getInstance().saveAllDocuments();
        ProjectManagerEx.getInstanceEx().blockReloadingProjectOnExternalChanges();

        init(env);

        File from = new File(fromPath());
        File to = new File(toPath());

        if (to.exists()) to.delete();
        Files.copy(from.toPath(), to.toPath());

        // 重新从硬盘加载
        VirtualFile virtualFile = VfsUtil.findFileByIoFile(to, true);
        if (virtualFile != null) {
            virtualFile.refresh(true, false);
        }

        SaveAndSyncHandlerImpl.getInstance().refreshOpenFiles();
        VirtualFileManager.getInstance().refreshWithoutFileWatcher(false);
        ProjectManagerEx.getInstanceEx().unblockReloadingProjectOnExternalChanges();

        Notification notification = new Notification("tw tools", "Switch env", "Successful switch to " + env + ".", NotificationType.INFORMATION);
        notification.notify(e.getData(CommonDataKeys.PROJECT));
    }

    private static String fromPath() {
        return basePath() + "/" + files.get(env);
    }

    private static String toPath() {
        return basePath() + "/.env";
    }

    private static String basePath() {
        Project project = ProjectManager.getInstance().getOpenProjects()[0];

        return project.getBasePath();
    }
}
