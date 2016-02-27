package com.example.shruj.imdbapp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by shruj on 02/24/2016.
 */
public class RequestParam {
    String baseUrl;
    String method;
    HashMap<String, String> params = new HashMap<>();

    public RequestParam(String method, String baseUrl) {
        super();
        this.method = method;
        this.baseUrl = baseUrl;
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public String getEncodedParams() {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            try {
                String value = URLEncoder.encode(params.get(key), "UTF-8");
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(key + "=" + value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String getEncodedUrl() {
        return this.baseUrl + "?" + getEncodedParams();
    }

    public HttpURLConnection setUpConnection() throws IOException {
        if (method.equals(Constants.GET_METHOD)) {
            URL url = new URL(getEncodedUrl());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(Constants.GET_METHOD);
            return httpURLConnection;
        } else {
            URL url = new URL(this.baseUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(Constants.POST_METHOD);
            httpURLConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
            outputStreamWriter.write(getEncodedParams());
            outputStreamWriter.flush();
            return httpURLConnection;
        }
    }

}
