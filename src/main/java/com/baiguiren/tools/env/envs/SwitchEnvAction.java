package com.baiguiren.tools.env.envs;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class SwitchEnvAction {

    private static String env;

    private static HashMap<String, String> domains = new HashMap<>();

    private static HashMap<String, String> files = new HashMap<>();

    private static void init(String env) {
        SwitchEnvAction.env = env;

        putDomains();
        putFiles();
    }

    private static void putDomains() {
        domains.put("qa1", "qa1.api.86yqy.com");
        domains.put("qa2", "qa2.api.86yqy.com");
        domains.put("qa3", "qa3.api.86yqy.com");
        domains.put("demo", "demo.api.86yqy.com");
    }

    private static void putFiles() {
        files.put("qa1", ".env.qa1");
        files.put("qa2", ".env.qa2");
        files.put("qa3", ".env.qa3");
        files.put("demo", ".env.release");
    }

    public static void switchToEnv(String env) throws IOException {
        init(env);

        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        String basePath = project.getBasePath();

        File from = new File(basePath + "/" + resolveEnv());
        File to = new File(basePath + "/.env");
        System.out.println(from.toPath());
        System.out.println(to.toPath());

        try {
            Files.delete(to.toPath());
        } catch (IOException e) {
//            e.printStackTrace();
        }
        Files.copy(from.toPath(), to.toPath());

        replaceHost();
        if (env.equals("demo")) {
            replaceDemoHost();
        }
    }

    private static void replaceDemoHost() {
        BufferedReader reader;
        try {
            StringBuilder content = new StringBuilder();
            reader = new BufferedReader(new FileReader(envPath()));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (line.contains("DB_HOST") && !line.contains("MONGO")) {
                    line = line.replaceAll("rm-wz9obs5n6o606f5h7rw\\.mysql\\.rds\\.aliyuncs\\.com", "rm-wz9obs5n6o606f5h7ao.mysql.rds.aliyuncs.com");
                }
                content.append(line);
            }
            reader.close();

            filePutContents(toPath(), content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void replaceHost() {
        try {
            File file = new File(envPath());
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            content = content.replaceAll("127\\.0\\.0\\.1", resolveDomain());
            filePutContents(toPath(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String envPath() {
        return basePath() + "/" + resolveEnv();
    }

    private static String toPath() {
        return basePath() + "/.env";
    }

    private static String basePath() {
        Project project = ProjectManager.getInstance().getOpenProjects()[0];

        return project.getBasePath();
    }

    private static String resolveEnv() {
        return files.get(env);
    }

    private static String resolveDomain() {
        return domains.get(env);
    }

    private static void filePutContents(String filename, String data) {
        try {
            FileWriter fstream = new FileWriter(filename, false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
