package com.baiguiren.tools.runner;

import com.baiguiren.tools.utils.ProjectUtil;
import com.baiguiren.tools.utils.TerminalUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Pytest extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String relativePath = ProjectUtil.getRelativePath(e);

        try {
            TerminalUtil.runCommand("pytest " + relativePath, e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
