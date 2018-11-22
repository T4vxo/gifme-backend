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
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author Administratör
 */
public abstract class HttpUtils {
    public static HttpResponse getResponse(String url) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpUriRequest request = new HttpGet(url);
        
        return client.execute(request);
    }
    
    public static String getResponseEntity(String url) throws IOException {
        HttpResponse res = getResponse(url);
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
    
    public static JSONObject getResponseJson(String url) throws IOException {
        return JSONObject.parseObject(getResponseEntity(url));
    }
}
