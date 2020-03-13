package com.baiguiren.tools.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;

import java.awt.*;

public class ProjectUtil {
    public static Project getActiveProject() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        Project activeProject = null;

        for (Project project : projects) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive()) {
                activeProject = project;
            }
        }

        return activeProject;
    }

    /**
     * 获取光标所在行
     *
     * @param e AnActionEvent
     * @return 光标所在行的内容
     */
    public static String getLineContent(AnActionEvent e) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) return "";
        Document document = editor.getDocument();
        final CaretModel caretModel = editor.getCaretModel();

        int caretOffset = caretModel.getOffset();
        int lineNum = document.getLineNumber(caretOffset);
        int lineStartOffset = document.getLineStartOffset(lineNum);
        int lineEndOffset = document.getLineEndOffset(lineNum);

        return document.getText(new TextRange(lineStartOffset, lineEndOffset));
    }

    /**
     * 获取当前 editor 的相对路径
     *
     * @param e AnActionEvent
     * @return editor 编辑文件的相对路径
     */
    public static String getRelativePath(AnActionEvent e) {
        VirtualFile vfs = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        Project project = e.getProject();
        String basePath = project.getBasePath();
        String relativePath = vfs.getPath().replace(basePath, "");
        relativePath = relativePath.substring(1);

        return relativePath;
    }

    /**
     * 获取光标所在行的方法名
     *
     * @param e AnActionEvent
     * @return 光标所在行的方法
     */
    public static String getMethodName(AnActionEvent e) {
        String line = getLineContent(e);

        if (!line.contains("def")) {
            return "";
        }

        line = line.trim();
        line = line.replace("def ", "");
        line = line.substring(0, line.indexOf('('));

        return line;
    }
}
