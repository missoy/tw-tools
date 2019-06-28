package com.baiguiren.tools.exclude;

import com.intellij.conversion.CannotConvertException;
import com.intellij.conversion.ModuleSettings;
import com.intellij.conversion.impl.ConversionContextImpl;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Exclude extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getData(CommonDataKeys.PROJECT);

        try {
            ConversionContextImpl context = new ConversionContextImpl(project.getBasePath());
            ModuleSettings moduleSettings = context.getModuleSettings(project.getBasePath() + "/.idea/modules.xml");
            moduleSettings.addExcludedFolder(new File(project.getBasePath() + "/tests"));
        } catch (CannotConvertException e1) {
            e1.printStackTrace();
        }
    }
}
