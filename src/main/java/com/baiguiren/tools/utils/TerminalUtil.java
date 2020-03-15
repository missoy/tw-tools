package com.baiguiren.tools.utils;

import com.baiguiren.tools.runner.ShTerminalRunner;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.plugins.terminal.TerminalView;

import java.io.IOException;

public class TerminalUtil {
    /**
     * 在 Terminal 窗口中运行命令
     *
     * @param command 要执行的命令
     * @param e AnActionEvent
     * @throws IOException e
     */
    public static void runCommand(String command, AnActionEvent e) throws IOException {
        if (e.getProject() == null) {
            return;
        }
//        TerminalView terminalView = TerminalView.getInstance(e.getProject());
//        terminalView.createLocalShellWidget(e.getProject().getBasePath()).executeCommand(command);
        ShTerminalRunner shTerminalRunner = new ShTerminalRunner(e.getProject());
        shTerminalRunner.run(command, e.getProject().getBasePath());
    }
}
