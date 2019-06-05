package com.baiguiren.tools.branch;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class V114 extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        SwitchBranch.switchTo("dev/v1.1.4");
    }
}
