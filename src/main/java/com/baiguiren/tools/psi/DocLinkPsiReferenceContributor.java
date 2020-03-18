package com.baiguiren.tools.psi;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.jetbrains.python.psi.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;

public class DocLinkPsiReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(StringLiteralExpression.class)
                        .withText(StandardPatterns.string().startsWith("'/skynetapi/api")),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        if (element.getText().startsWith("'/skynetapi/api")) {
//                            System.out.println("matched");
//                            System.out.println(element.getText());
                            return new PsiReference[]{
                                    new DocLinkPsiReference(element, element.getTextRange())
                            };
                        }
                        return new PsiReference[0];
                    }
                }
        );
    }
}
