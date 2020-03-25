package com.baiguiren.tools.widget;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyStatusBarProvider implements StatusBarWidgetProvider {
    @Nullable
    @Override
    public StatusBarWidget getWidget(@NotNull Project project) {
       return new EnvWidget();
    }

    @NotNull
    @Override
    public String getAnchor() {
        return StatusBar.Anchors.before(StatusBar.StandardWidgets.COLUMN_SELECTION_MODE_PANEL);
    }
}
