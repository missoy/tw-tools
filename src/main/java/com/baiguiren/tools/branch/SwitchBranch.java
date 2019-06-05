package com.baiguiren.tools.branch;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vcs.VcsActions;

import java.util.Collection;

public class SwitchBranch {
    private static String branch;

    public static void switchTo(String to) {
        branch = to;
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        Collection repositories = VcsRepositoryManager.getInstance(project).getRepositories();
        repositories.forEach(repository -> System.out.println(repository.toString()));
        Repository repository = (Repository) repositories.iterator().next();
        System.out.println(repository.getCurrentBranchName());
        System.out.println(repository.getRoot());
        System.out.println(repository.getVcs());
        System.out.println(repository.getVcs().getCheckoutProvider());
        System.out.println(repository.getVcs().getCheckoutProvider().getVcsName());
//        repository.getVcs().getCheckoutProvider().doCheckout(project, );
    }
}
