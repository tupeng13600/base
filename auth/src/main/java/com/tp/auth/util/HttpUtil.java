package com.tp.auth.util;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by 22670 on 2017/9/18.
 */
public abstract class HttpUtil {

    /**
     * GET 请求
     *
     * @param host
     * @param path
     * @param <T>
     * @return
     */
    public static <T> Response<T> get(String host, String path) {
        try {
            URL url = new URL(host + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            initHeader(connection, "GET");
            connection.connect();
            return getResponse(connection.getResponseCode() == 200 ? connection.getInputStream() : connection.getErrorStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post 请求
     *
     * @param host
     * @param path
     * @param requestParam
     * @param <T>
     * @return
     */
    public static <T> Response<T> post(String host, String path, Object requestParam) {
        try {
            URL url = new URL(host + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            initHeader(connection, "POST");
            if (null != requestParam) {
                connection.setDoOutput(true);
                initJsonArgs(connection, requestParam);
            } else {
                connection.connect();
            }
            return getResponse(connection.getResponseCode() == 200 ? connection.getInputStream() : connection.getErrorStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Response response = post("http://localhost:8412", "/auth/login", null);
        System.out.print(response);
    }

    private static <T> Response<T> getResponse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder builder = new StringBuilder();
        String strRead;
        while ((strRead = reader.readLine()) != null) {
            builder.append(strRead);
        }
        reader.close();
        inputStream.close();
        if (StringUtils.isNotEmpty(builder.toString())) {
            return new Gson().fromJson(builder.toString(), Response.class);
        }
        return null;
    }

    private static void initHeader(HttpURLConnection connection, String method) throws ProtocolException {
        connection.setRequestMethod(method);
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Accept-Language", "zh-cn");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
    }

    private static void initJsonArgs(HttpURLConnection connection, Object param) throws IOException {
        OutputStream out = connection.getOutputStream();
        out.write(new Gson().toJson(param).getBytes());
        out.flush();
        out.close();
    }

}
