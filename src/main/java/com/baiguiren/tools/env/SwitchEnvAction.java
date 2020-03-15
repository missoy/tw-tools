package com.baiguiren.tools.env;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class SwitchEnvAction extends AnAction {
    private String env;

    public SwitchEnvAction(@Nullable @Nls(capitalization = Nls.Capitalization.Title) String text) {
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
