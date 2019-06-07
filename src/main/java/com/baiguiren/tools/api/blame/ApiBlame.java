package com.baiguiren.tools.api.blame;

import com.intellij.ide.BrowserUtil;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;

class ApiBlame {
    static void uri(AnActionEvent e, String method) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor != null) {
            final CaretModel caretModel = editor.getCaretModel();
            String uri = caretModel.getCurrentCaret().getSelectedText();

            try {
                Api api = new Api(Request.send(uri, method));
                BrowserUtil.open(api.getDocsUrl());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
