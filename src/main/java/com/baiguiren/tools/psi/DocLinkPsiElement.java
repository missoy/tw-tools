package com.baiguiren.tools.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.jetbrains.python.psi.impl.PyStringLiteralExpressionImpl;
import com.jetbrains.python.pyi.PyiUtil;
import org.jetbrains.annotations.NotNull;

public class DocLinkPsiElement extends PyStringLiteralExpressionImpl {
    public DocLinkPsiElement(ASTNode astNode) {
        super(astNode);
    }

    @NotNull
    public PsiElement getNavigationElement() {
        PsiElement element = PyiUtil.getOriginalElement(this);
        PsiElement var10000 = element != null ? element : super.getNavigationElement();

        System.out.println("getNavigationElement");

        return var10000;
    }
}
