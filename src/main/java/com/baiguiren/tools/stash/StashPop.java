package com.baiguiren.tools.stash;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.ide.SaveAndSyncHandlerImpl;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.vfs.VirtualFileManager;
import git4idea.commands.Git;
import git4idea.commands.GitCommandResult;
import git4idea.repo.GitRepository;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class StashPop extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getData(CommonDataKeys.PROJECT);
        Collection<Repository> repositories = VcsRepositoryManager.getInstance(project).getRepositories();

        project.save();
        FileDocumentManager.getInstance().saveAllDocuments();
        ProjectManagerEx.getInstanceEx().blockReloadingProjectOnExternalChanges();

        ProgressManager.getInstance().run(new Task.Modal(project, "Stash Pop All", false) {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                indicator.setText("Stash pop all");
                indicator.setIndeterminate(true);

                repositories
                        .stream()
                        .map(obj -> (GitRepository) obj)
                        .forEach(gitRepository -> {
                            GitCommandResult result = Git.getInstance().stashPop(gitRepository, (line, key) -> {});
                            System.out.println(result.getOutput());
                        });
            }
        });

        Notification notification = new Notification("tw tools", "Stash Helper", "Successful stash pop.", NotificationType.INFORMATION);
        notification.notify(e.getData(CommonDataKeys.PROJECT));

        SaveAndSyncHandlerImpl.getInstance().refreshOpenFiles();
        VirtualFileManager.getInstance().refreshWithoutFileWatcher(false);
        ProjectManagerEx.getInstanceEx().unblockReloadingProjectOnExternalChanges();
    }
}
