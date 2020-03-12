package com.baiguiren.tools.runner;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public abstract class ShRunner {
    protected final Project myProject;

    public ShRunner(@NotNull Project project) {
        myProject = project;
    }

    public abstract void run(@NotNull String command, @NotNull String workingDirectory);

    public abstract boolean isAvailable(@NotNull Project project);
}
