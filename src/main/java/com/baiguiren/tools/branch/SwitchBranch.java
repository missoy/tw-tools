package com.baiguiren.tools.branch;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

import git4idea.branch.GitBrancher;
import git4idea.repo.GitRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SwitchBranch {
    public static void switchTo(String to) {
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        GitBrancher gitBrancher = GitBrancher.getInstance(project);
        Collection<Repository> repositories = VcsRepositoryManager.getInstance(project).getRepositories();

        List<GitRepository> gitRepositories = repositories
                                                .stream()
                                                .map(obj -> (GitRepository) obj)
                                                .collect(Collectors.toList());

        // 本地没有那个分支的时候创建, 同时需要远程分支也存在(否则会报错)
        List<GitRepository> forCheckoutRepositories = new ArrayList<>();
        for (GitRepository gitRepository : gitRepositories) {
            if (gitRepository.getBranches().findLocalBranch("refs/heads/" + to) == null) {
                if (gitRepository.getBranches().getRemoteBranches().stream().anyMatch(gitRemoteBranch -> gitRemoteBranch.toString().equals("refs/remotes/origin/" + to))) {
                    forCheckoutRepositories.add(gitRepository);
                }
            }
        }
        if (!forCheckoutRepositories.isEmpty())
            gitBrancher.checkoutNewBranchStartingFrom(to, "refs/remotes/origin/" + to, forCheckoutRepositories, () -> {});

        gitBrancher.checkout(to, false, gitRepositories, () -> System.out.println("Finish checkout."));

        Notification notification = new Notification("tw tools", "Switch Branch", "Successful switch to " + to + ".", NotificationType.INFORMATION);
        notification.notify(project);
    }
}
