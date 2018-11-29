/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author Administratör
 */
public abstract class HttpUtils {

    public static HttpResponse getResponse(String url, Method method, JSONObject jsonBody) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpUriRequest request;

        HttpEntity body = null;
        
        if (jsonBody != null) {
            body = new StringEntity(jsonBody.toJSONString());
        }
        
        switch (method) {
            case POST:
                request = new HttpPost(url);
                if (body != null) {
                    ((HttpPost) request).setEntity(body);
                }
                break;

            case DELETE:
                request = new HttpDelete(url);
                break;

            case PUT:
                request = new HttpPut(url);
                if (body != null) {
                    ((HttpPut) request).setEntity(body);
                }
                break;

            default:
                //  Defaults to GET
                request = new HttpGet(url);
                break;
        }
        
        request.addHeader("Accept", "application/json");

        return client.execute(request);
    }

    public static HttpResponse getResponse(String url) throws IOException {
        return getResponse(url, Method.GET, null);
    }

    public static String getResponseEntity(String url, Method method, JSONObject jsonBody) throws IOException {
        System.out.println("Requesting: " + url);
        HttpResponse res = getResponse(url, method, jsonBody);
        HttpEntity entity = res.getEntity();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(entity.getContent())
        );
        StringBuilder entityStringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            entityStringBuilder.append(line);
            entityStringBuilder.append("\n");
        }

        return entityStringBuilder.toString();
    }

    public static String getResponseEntity(String url) throws IOException {
        return getResponseEntity(url, Method.GET, null);
    }

    public static JSONObject getResponseJson(String url, Method method, JSONObject jsonBody) throws IOException {
        return JSONObject.parseObject(getResponseEntity(url));
    }

    public static JSONObject getResponseJson(String url) throws IOException {
        return getResponseJson(url, Method.GET, null);
    }

    public enum Method {
        GET, POST, PUT, DELETE
    }
}
