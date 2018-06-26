package com.weimob.authorization.center.accesstoken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class HttpClientUtil {

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void close() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadDataByGet(String url) {

        String header = "application/json;charset=UTF-8";

        return loadDataByGet(url, header);
    }

    public static String loadDataByGet(String url, String header) {

        HttpGet httpPost = new HttpGet(url);

        httpPost.addHeader("Content-Type", header);

        // Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {//
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };

        String responseBody = null;
        try {
            responseBody = httpClient.execute(httpPost, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBody;
    }

    public static String loadDataByPost(String url, String params) {
        String charset = "utf-8";
        HttpPost httpPost = new HttpPost(url);

        System.out.println(url);
        //httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

        if (!StringUtils.isEmpty(params)) {
            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(params, charset);
            stringEntity.setContentEncoding(charset);
            httpPost.setEntity(stringEntity);
        }

        // Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {//
                int status = response.getStatusLine().getStatusCode();
                System.out.println(response.toString());
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    //throw new ClientProtocolException("Unexpected response status: " + status);
                    return null;
                }
            }
        };

        String responseBody = null;
        try {
            responseBody = httpClient.execute(httpPost, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBody;
    }


    public static void main(String[] args) {

        String params = "{ \n" +
                "    \"category_pid\": \"1\"\n" +
                "} ";
        String token = "8952a8ac-d253-4a0a-8978-02c080cc2cc7";

        String testApi = "https://dopen.weimob.com/api/1_0/wangpu/Category/Get?accesstoken=" + token;
        String s = loadDataByPost(testApi, params);
        System.out.println(s);


       /* AccessTokenTools tokenTools = new AccessTokenTools();
        AppInfo appInfo = new AppInfo();
        tokenTools.setAppInfo(appInfo);
        tokenTools.setRefreshToken("8cf242ff-ecc0-4e47-a064-c3d07790df2c");
        tokenTools.loadAccessToken();*/


    }

}
