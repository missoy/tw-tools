package com.baiguiren.tools.runner;

import com.baiguiren.tools.utils.NotificationUtil;
import com.intellij.execution.Executor;
import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.terminal.JBTerminalWidget;
import com.intellij.ui.TextComponent;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.HashMap;

public class Pytest extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile vfs = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        NotificationUtil.notification(vfs.getPath());
        Project project = e.getProject();
        String basePath = project.getBasePath();
        String relativePath = vfs.getPath().replace(basePath, "");
        relativePath = relativePath.substring(1);
        NotificationUtil.notification(relativePath);

//        LocalTerminalDirectRunner localTerminalDirectRunner = LocalTerminalDirectRunner.createTerminalRunner(e.getProject());
//        TerminalView terminalView = TerminalView.getInstance(e.getProject());
//        terminalView.createNewSession(localTerminalDirectRunner);
//        terminalView.getTerminalRunner().run();

//        ToolWindow window = ToolWindowManager.getInstance(e.getProject()).getToolWindow(TerminalToolWindowFactory.TOOL_WINDOW_ID);
//        if (window == null) return;
//        window.activate(new Runnable() {
//            @Override
//            public void run() {
//                NotificationUtil.notification("window activate!");
//            }
//        });
//
//        window.getAnchor().isSplitVertically();
    }
}
