package com.baiguiren.tools.psi;

import com.intellij.openapi.paths.WebReference;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DocLinkPsiReference extends WebReference {
    public DocLinkPsiReference(@NotNull PsiElement element, TextRange textRange, @Nullable String url) {
        super((DocLinkPsiElement) element, textRange, url);
        System.out.println("DocLinkPsiReference");
    }

    public String getUrl() {
        System.out.println("get url");
        return "http://www.google.com?a=1";
    }
}
