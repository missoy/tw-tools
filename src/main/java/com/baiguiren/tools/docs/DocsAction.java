package com.baiguiren.tools.docs;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocsAction extends AnAction {
    private static AnActionEvent anActionEvent;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        openInBrowser(e);
    }

    /**
     * 在浏览器打开文档链接
     *
     * @param e AnActionEvent
     */
    private static void openInBrowser(AnActionEvent e) {
        anActionEvent = e;
        String line = getLineContent(e);
        String urlStr = matchUrl(line);
        if (urlStr.equals("")) {
            return;
        }

        urlStr = urlStr.replace("qa1.api.86yqy.com", getEnvDomain());
        open(urlStr);
    }

    /**
     * @return 获取 .env 配置的 url 对应的域名
     */
    private static String getEnvDomain() {
        String basePath = anActionEvent.getProject().getBasePath();

        try {
            List<String> lines = Files.readAllLines(Paths.get(basePath + "/.env"));
            for (String line: lines) {
                if (StringUtil.startsWith(line, "url=")) {
                    line = line.replace("url=", "");
                    line = line.trim();
                    line = line.replace("http://", "");
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * @param e AnActionEvent
     * @return 光标所在行的内容
     */
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

    /**
     * 匹配光标所在行的 url
     *
     * @param line 光标所在行
     * @return url
     */
    private static String matchUrl(String line) {
        String pattern = "[\\s\\S]*(http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])[\\s\\S]*";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);

        if (m.find()) {
            return m.group(1);
        }

        return "";
    }

    /**
     * 在浏览器中打开对应的 url
     *
     * @param url 需要打开的 url
     */
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
