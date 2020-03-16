package com.baiguiren.tools.env;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SwitchEnvAction extends AnAction {
    private String env;

    public SwitchEnvAction(String text) {
        super(text);
        this.env = text;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            SwitchEnv.switchToEnv(e, this.env);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
