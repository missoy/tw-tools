package com.baiguiren.tools.env;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SwitchEnvActionGroup extends ActionGroup {
    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent e) {
        String[] envs = {
                "qa1",
                "qa2",
                "qa3",
                "demo",
        };

        ArrayList<AnAction> anActions = new ArrayList<>();
        for (String env : envs) {
            anActions.add(new SwitchEnvAction(env));
        }

        return anActions.toArray(new AnAction[envs.length]);
    }
}
