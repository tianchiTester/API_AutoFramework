package com.qa.restclient;


import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class RestClient {
    final static Logger Log = Logger.getLogger(RestClient.class.getClass());

    //get接口带header
    public CloseableHttpResponse getApi(String url , Map<String,String> map) throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        for(Map.Entry<String,String> header: map.entrySet() ){
            get.addHeader(header.getKey(),header.getValue());
        }
        CloseableHttpResponse httpResponse = httpClient.execute(get);

        return httpResponse;
    }

    //普通get接口
    public CloseableHttpResponse getApi(String url) throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        CloseableHttpResponse httpResponse = httpClient.execute(get);
        System.out.println(httpResponse);

        return httpResponse;
    }

    //post接口
    public CloseableHttpResponse postApi(String url, String entityString, HashMap<String,String> headermap) throws ClientProtocolException, IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new StringEntity(entityString));
        //加载请求头到httppost对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httppost.addHeader(entry.getKey(), entry.getValue());
        }
        //发送post请求
        CloseableHttpResponse httpResponse = httpclient.execute(httppost);

        return httpResponse;
    }


    //delete方法
    public CloseableHttpResponse deleteApi(String url) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpdel = new HttpDelete(url);
        //发送delete请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpdel);
        return httpResponse;
    }
}
