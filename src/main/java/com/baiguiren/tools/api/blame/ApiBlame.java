package com.baiguiren.tools.api.blame;

import com.intellij.ide.BrowserUtil;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import org.codehaus.jettison.json.JSONException;

class ApiBlame {
    static void uri(AnActionEvent e, String method) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor != null) {
            final CaretModel caretModel = editor.getCaretModel();
            String uri = caretModel.getCurrentCaret().getSelectedText();


            String response = null;
            try {
                response = Request.send(uri, method);
            } catch (Exception e1) {
                // 网络异常
                Notification notification = new Notification("Api Blame", "Api blame network error", "网络错误", NotificationType.ERROR);
                notification.notify(e.getData(CommonDataKeys.PROJECT));
                e1.printStackTrace();
            }

            if (response != null) {
                try {
                    Api api = new Api(response);
                     BrowserUtil.open(api.getDocsUrl());
                } catch (JSONException e1) {
                    Notification notification = new Notification("Api Blame", "Api blame fails", "返回数据格式错误", NotificationType.ERROR);
                    notification.notify(e.getData(CommonDataKeys.PROJECT));
                    e1.printStackTrace();
                }
            }
        }
    }
}
