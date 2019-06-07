package com.baiguiren.tools.api.blame;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class Post extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ApiBlame.uri(e, "post");
    }
}
