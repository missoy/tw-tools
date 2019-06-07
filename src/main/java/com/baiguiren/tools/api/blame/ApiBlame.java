package com.baiguiren.tools.api.blame;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import org.codehaus.jettison.json.JSONArray;

public class ApiBlame {
    public static void uri(AnActionEvent e, String method) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor != null) {
            final CaretModel caretModel = editor.getCaretModel();
            System.out.println(caretModel.getCurrentCaret().getSelectedText());
        }

        JSONArray jsonArray = new JSONArray();
        System.out.println("jsonArray: " + jsonArray);
        Request.send("", method);
    }
}
