package com.baiguiren.tools.branch;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.AbstractVcs;
import com.intellij.openapi.vcs.versionBrowser.CommittedChangeList;
import org.jetbrains.annotations.NotNull;

public class GitVcs extends AbstractVcs<CommittedChangeList> {
    public GitVcs(@NotNull Project project, String name) {
        super(project, name);
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "git";
    }

    @Override
    public Configurable getConfigurable() {
        return null;
    }
}
