package com.baiguiren.tools.env;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Qa2 extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            SwitchEnvAction.switchToEnv("qa2");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
