package com.baiguiren.tools.branch;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

import git4idea.branch.GitBrancher;
import git4idea.repo.GitRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SwitchBranch {
    public static void switchTo(String to) {
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        Collection<Repository> repositories = VcsRepositoryManager.getInstance(project).getRepositories();

        List<GitRepository> gitRepositories = repositories
                                                .stream()
                                                .map(obj -> (GitRepository) obj)
                                                .collect(Collectors.toList());

        GitBrancher gitBrancher = GitBrancher.getInstance(project);
        gitBrancher.checkout(to, false, gitRepositories, () -> System.out.println("Finish checkout."));
    }
}
