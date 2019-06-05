package com.baiguiren.tools.branch;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class Qa1 extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        SwitchBranch.switchTo("qa/qa1");
    }
}
