package com.baiguiren.tools.docs;

import com.baiguiren.tools.utils.NotificationUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        String urlStr = getUri(line);
        if (urlStr.equals("")) {
            return;
        }
        if (!urlStr.startsWith("/")) {
            urlStr = "/" + urlStr;
        }

        urlStr = "http://" + getEnvDomain() + urlStr;
//        NotificationUtil.notification(urlStr);
        open(urlStr);
    }

    /**
     * @return 获取 .env 配置的 url 对应的域名
     */
    private static String getEnvDomain() {
        String basePath = anActionEvent.getProject().getBasePath();

        FileDocumentManager.getInstance().saveAllDocuments();

        String content = readFileContent(basePath + "/.env");
//        NotificationUtil.notification(content);
        String[] lines = content.trim().split("\n");
        for (String line: lines) {
            if (StringUtil.startsWith(line, "url=")) {
                line = line.replace("url=", "");
                line = line.trim();
                line = line.replace("http://", "");
                return line;
            }
        }

        return "";
    }

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr + "\n");
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
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
     * 获取 uri
     *
     * @param line doc_uri 所在行
     * @return uri
     */
    private static String getUri(String line)
    {
        line = line.split("=")[1];
        line = line.replace("'", "");
        line = line.replace(",\n", "");

        return line;
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
