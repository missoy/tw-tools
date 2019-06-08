package com.baiguiren.tools.api.blame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

class Request {
    static String send(String uri, String method) throws Exception {
        String url = "http://api-blame.baiguiren.com";
        HashMap<String, String> params = new HashMap<>();
        params.put("url", uri);
        params.put("method", method.toUpperCase());

        return sendPost(url, params);
    }

    // HTTP POST request
    private static String sendPost(String url, HashMap<String, String> params) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");

        // Send post request
        con.setDoOutput(true);

        // 获取URLConnection对象对应的输出流
        PrintWriter out = new PrintWriter(con.getOutputStream());
        // 发送请求参数
        out.print(urlEncodeUTF8(params));
        // flush输出流的缓冲
        out.flush();

//        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private static String urlEncodeUTF8(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8");
    }

    private static String urlEncodeUTF8(Map<?, ?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }
}
