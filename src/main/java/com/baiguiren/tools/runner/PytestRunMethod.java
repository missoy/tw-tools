package com.baiguiren.tools.runner;

import com.baiguiren.tools.utils.ProjectUtil;
import com.baiguiren.tools.utils.TerminalUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PytestRunMethod extends AnAction {
    private static AnActionEvent e = null;

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        e = anActionEvent;
        String relativePath = ProjectUtil.getRelativePath(e);

        // 光标所在方法名
        String methodName = ProjectUtil.getMethodName(e);
        if (methodName.equals("")) {
            return;
        }
        // 当前激活的编辑器里的文件的类名称
        String className = getClassName();
        if (className.equals("")) {
            return;
        }

        String path = relativePath + "::" + className + "::" + methodName;
        try {
            TerminalUtil.runCommand("pytest " + path, e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取文件对应的类名称
     *
     * @return 文件中对应的类名称
     */
    private static String getClassName() {
        VirtualFile vfs = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        Path path = Paths.get(vfs.getPath());
        String fileName = path.getFileName().toString();
        fileName = fileName.replace(".py", "");

        // "test_case" => "TestCase"
        StringBuilder sb = new StringBuilder(fileName);
        for (int i = 0; i < sb.length(); i++) {
            if (i == 0) {
                sb.replace(0, 1, String.valueOf(Character.toUpperCase(sb.charAt(0))));
            }
            if (sb.charAt(i) == '_') {
                sb.deleteCharAt(i);
                sb.replace(i, i+1, String.valueOf(Character.toUpperCase(sb.charAt(i))));
            }
        }

        return sb.toString();
    }
}
