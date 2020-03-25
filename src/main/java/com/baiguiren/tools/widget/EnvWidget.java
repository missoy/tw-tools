package com.baiguiren.tools.widget;

import com.baiguiren.tools.env.SwitchEnvAction;
import com.baiguiren.tools.utils.NotificationUtil;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.impl.SimpleDataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.CustomStatusBarWidget;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.impl.status.TextPanel;
import com.intellij.ui.ClickListener;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class EnvWidget implements StatusBarWidget.Multiframe, CustomStatusBarWidget {
    public static final String WIDGET_ID = "EnvWidget";

    private final TextPanel myComponent;

    protected StatusBar myStatusBar;

    public EnvWidget() {
        myComponent = new TextPanel.WithIconAndArrows();
        myComponent.setBorder(WidgetBorder.WIDE);
    }

    @NotNull
    @Override
    public String ID() {
        return WIDGET_ID;
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {
        myStatusBar = statusBar;
        Disposer.register(myStatusBar, this);
        requestUpdate();
        new ClickListener() {
            @Override
            public boolean onClick(@NotNull MouseEvent event, int clickCount) {
//                NotificationUtil.notification("clicked!");
                requestUpdate();
                showPopup();
                return true;
            }
        }.installOn(myComponent);
    }

    private void showPopup() {
        ListPopup popup = createPopup(myComponent);
        RelativePoint pos = JBPopupFactory.getInstance().guessBestPopupLocation(myComponent);
        popup.showInScreenCoordinates(myComponent, pos.getScreenPoint());
    }

    ListPopup createPopup(Component componentParent) {
        DataContext dataContext = wrapInDataContext(componentParent);
        DefaultActionGroup group = new DefaultActionGroup();
        ArrayList<String> envs = envs();
        for (String env : envs) {
            group.add(new SwitchEnvAction(env));
        }

        return JBPopupFactory.getInstance().createActionGroupPopup("Switch Env",
                group, dataContext, JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, false);
    }

    @NotNull
    private static DataContext wrapInDataContext(Component componentParent) {
        DataContext parent = DataManager.getInstance().getDataContext(componentParent);
        return SimpleDataContext.getSimpleContext(
                ContainerUtil.<String, Object>immutableMapBuilder()
                        .put("componentParent", componentParent)
                        .build(),
                parent);
    }

    private static ArrayList<String> envs() {
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        File[] files = new File(project.getBasePath()).listFiles();
        ArrayList<String> envs = new ArrayList<>();
        for (File file : files) {
            if (file.getName().startsWith(".env.dev.")) {
                envs.add(file.getName().replace(".env.dev.", ""));
            }
        }
        Collections.sort(envs);
        return envs;
    }

    public void requestUpdate() {
//        NotificationUtil.notification("requestUpdate !");
        myComponent.setText("Env: qa1");
    }

    private static String currentEnv() {
        String envContent = currentEnvContent();
        String[] envs = {"qa1", "qa2", "qa3", "release", "develop"};
        for (String env : envs) {
            // Python
            if (envContent.contains(env + ".swoole")) {
                return env;
            }
        }
        return "";
    }

    private static String currentEnvContent() {
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        if (project == null) {
            return "";
        }
        String basePath = project.getBasePath();
        if (basePath == null) {
            return "";
        }

        Path envPath = Paths.get(basePath, ".env");
        if (!Files.isRegularFile(envPath)) {
            return "";
        }

        String content = "";
        try {
            content = new String(Files.readAllBytes(envPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }


    @Override
    public void dispose() {
        myStatusBar = null;
    }

    @Override
    public JComponent getComponent() {
        return myComponent;
    }

    @Override
    public StatusBarWidget copy() {
        return new EnvWidget();
    }
}
