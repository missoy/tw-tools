package com.baiguiren.tools.branch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baiguiren.tools.utils.NotificationUtil;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;

public class SwitchBranchActionGroup extends ActionGroup {
    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent e) {
        String[] branches;
        try {
            branches = getBranchesFromRemote();
        } catch (IOException ex) {
            branches = new String[]{
                    "qa/qa1",
                    "qa/qa2",
                    "qa/qa3",
                    "release",
                    "master",
            };
            NotificationUtil.notification(ex.getMessage());
        }

        ArrayList<AnAction> anActions = new ArrayList<>();
        for (String branch : branches) {
            anActions.add(new SwitchBranchAction(branch));
        }

        return anActions.toArray(new AnAction[branches.length]);
    }

    private static String[] getBranchesFromRemote() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://demo.devadmin.86yqy.com/versions").build();
        Response response = client.newCall(request).execute();
        String jsonStr = response.body().string();

        JSONArray jsonArray = JSON.parseArray(jsonStr);
        Object[] objects = jsonArray.toArray();
        String[] branches = new String[objects.length];

        for (int i = 0; i < objects.length; i++) {
            branches[i] = (String) objects[i];
        }
        return branches;
    }
}
