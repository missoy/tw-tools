package com.baiguiren.tools.runner;

import com.baiguiren.tools.utils.ProjectUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class Pytest extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        String basePath = project.getBasePath();
        String relativePath = ProjectUtil.getRelativePath(e);

        ShTerminalRunner shTerminalRunner = new ShTerminalRunner(e.getProject());
        shTerminalRunner.run("pytest " + relativePath + "\n", basePath);
    }
}
