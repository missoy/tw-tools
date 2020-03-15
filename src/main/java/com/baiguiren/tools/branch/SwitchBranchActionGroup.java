package com.baiguiren.tools.branch;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SwitchBranchActionGroup extends ActionGroup {
    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent e) {
        String[] branches = {
                "qa/qa1",
                "qa/qa2",
                "qa/qa3",
                "dev/v1.3.2",
                "dev/v1.3.3",
                "dev/v1.3.4",
                "dev/v1.4.0",
                "release",
                "master",
        };

        ArrayList<AnAction> anActions = new ArrayList<>();
        for (String branch : branches) {
            anActions.add(new SwitchBranchAction(branch));
        }

        return anActions.toArray(new AnAction[branches.length]);
    }
}
