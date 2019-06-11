package com.baiguiren.tools.env;

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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        domains.put("demo", "demo.api.86yqy.com");
    }

    private static void putFiles() {
        files.put("qa1", ".env.qa1");
        files.put("qa2", ".env.qa2");
        files.put("qa3", ".env.qa3");
        files.put("demo", ".env.release");
    }

    public static void switchToEnv(AnActionEvent e, String env) throws IOException {
        Project project = e.getData(CommonDataKeys.PROJECT);
        project.save();
        FileDocumentManager.getInstance().saveAllDocuments();
        ProjectManagerEx.getInstanceEx().blockReloadingProjectOnExternalChanges();

        init(env);

        File from = new File(fromPath());
        File to = new File(toPath());

        if (to.exists()) to.delete();
        Files.copy(from.toPath(), to.toPath());

        replaceHost();

        // 重新从硬盘加载
        VirtualFile virtualFile = VfsUtil.findFileByIoFile(to, true);
        if (virtualFile != null) {
            virtualFile.refresh(true, false);
        }

        SaveAndSyncHandlerImpl.getInstance().refreshOpenFiles();
        VirtualFileManager.getInstance().refreshWithoutFileWatcher(false);
        ProjectManagerEx.getInstanceEx().unblockReloadingProjectOnExternalChanges();

        Notification notification = new Notification("tw tools", "Switch Env", "Successful switch to " + env + ".", NotificationType.INFORMATION);
        notification.notify(e.getData(CommonDataKeys.PROJECT));
    }

    private static String replaceDemoHost(String content) {
        StringBuilder sb = new StringBuilder();
        String[] lines = content.split("\n");

        for (String line: lines) {
            if (line.contains("DB_HOST") && !line.contains("MONGO")) {
                line = line.replaceAll("rm-wz9obs5n6o606f5h7rw\\.mysql\\.rds\\.aliyuncs\\.com", "rm-wz9obs5n6o606f5h7ao.mysql.rds.aliyuncs.com");
            }
            sb.append(line + "\n");
        }

        return sb.toString();
    }

    private static void replaceHost() {
        try {
            String content = readFile(fromPath());
            content = content.replaceAll("127\\.0\\.0\\.1", domains.get(env));

            if (env.equals("demo")) {
                content = replaceDemoHost(content);
            }

            filePutContents(toPath(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private static void filePutContents(String filename, String data) {
        try {
            FileWriter fstream = new FileWriter(filename, false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
