package com.baiguiren.tools.psi;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GoToDockLinkHandler implements GotoDeclarationHandler {
    @Nullable
    @Override
    public PsiElement[] getGotoDeclarationTargets(@Nullable PsiElement sourceElement, int offset, Editor editor) {
//        System.out.println("getGotoDeclarationTargets");
        if (sourceElement != null && sourceElement.getText().startsWith("'/skynetapi/api")) {
            System.out.println("1233");
        }
        return new PsiElement[0];
    }

    @Override
    public @Nullable String getActionText(@NotNull DataContext context) {
        return null;
    }
}
