package com.baiguiren.tools.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DocLinkPsiReference extends PsiReferenceBase<PsiElement> implements PsiReference {
    public DocLinkPsiReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        System.out.println("DocLinkPsiReference resolve");
        System.out.println(myElement);
        return myElement;
    }
}
