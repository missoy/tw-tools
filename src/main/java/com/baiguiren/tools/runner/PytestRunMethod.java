package com.baiguiren.tools.runner;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class PytestRunMethod extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile vfs = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        Project project = e.getProject();
        String basePath = project.getBasePath();
        String relativePath = vfs.getPath().replace(basePath, "");
        relativePath = relativePath.substring(1);

        ShTerminalRunner shTerminalRunner = new ShTerminalRunner(e.getProject());
        shTerminalRunner.run("pytest " + relativePath + "\n", basePath);
    }
}
