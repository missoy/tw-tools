package com.baiguiren.tools.docs;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocsAction {
    public static void openInBrowser(String env, AnActionEvent e) {
        String docsHost = env + ".api.86yqy.com";
        String line = getLineContent(e);
        String urlStr = matchUrl(line);
        if (urlStr.equals("")) {
            return;
        }

        urlStr = urlStr.replace("qa1.api.86yqy.com", docsHost);
        open(urlStr);
    }

    private static String getLineContent(AnActionEvent e) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) return "";
        Document document = editor.getDocument();
        final CaretModel caretModel = editor.getCaretModel();

        int caretOffset = caretModel.getOffset();
        int lineNum = document.getLineNumber(caretOffset);
        int lineStartOffset = document.getLineStartOffset(lineNum);
        int lineEndOffset = document.getLineEndOffset(lineNum);

        return document.getText(new TextRange(lineStartOffset, lineEndOffset));
    }

    private static String matchUrl(String line) {
        String pattern = "[\\s\\S]*(http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])[\\s\\S]*";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);

        if (m.find()) {
            return m.group(1);
        }

        return "";
    }

    private static void open(String url) {
        Runtime rt = Runtime.getRuntime();
        String os = StringUtil.toLowerCase(System.getProperty("os.name"));
        try {
            if (StringUtil.contains(os, "mac")) {
                rt.exec("open " + url);
            } else {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
