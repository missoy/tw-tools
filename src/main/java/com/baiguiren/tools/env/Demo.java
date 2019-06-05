package com.baiguiren.tools.env;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Demo extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            SwitchEnvAction.switchToEnv("demo");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
