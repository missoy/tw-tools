package com.baiguiren.tools.docs;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class Qa2 extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        DocsAction.openInBrowser("qa2", e);
    }
}
