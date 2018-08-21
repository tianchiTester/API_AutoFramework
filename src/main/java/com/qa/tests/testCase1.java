package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.qa.base.TestBase;
import com.qa.Parameters.postParameters;
import com.qa.restclient.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import static com.qa.util.TestUtil.dtt;

public class testCase1 extends TestBase {
    TestBase testBase;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    //host根url
    String host;
    //Excel路径
    String testCaseExcel;
    //header
    HashMap<String ,String> postHeader = new HashMap<String, String>();
    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        restClient = new RestClient();
        postHeader.put("Content-Type","application/json");
        //载入配置文件，接口endpoint
        host = prop.getProperty("Host");
        //载入配置文件，post接口参数
        testCaseExcel=prop.getProperty("testCase1data");

    }

    @DataProvider(name = "postData")
    public Object[][] post() throws IOException {
        return dtt(testCaseExcel,0);

    }

    @DataProvider(name = "get")
    public Object[][] get() throws IOException{
        //get类型接口
        return dtt(testCaseExcel,1);
    }

    @DataProvider(name = "delete")
    public Object[][] delete() throws IOException{
        //delete类型接口
        return dtt(testCaseExcel,2);
    }
    @Test(dataProvider = "postData")
    public void login(String loginUrl,String username, String passWord) throws Exception {
        //使用构造函数将传入的用户名密码初始化成登录请求参数
        postParameters loginParameters = new postParameters(username,passWord);
        //将登录请求对象序列化成json对象
        String userJsonString = JSON.toJSONString(loginParameters);
        //发送登录请求
        closeableHttpResponse = restClient.postApi(host+loginUrl,userJsonString,postHeader);
        //从返回结果中获取状态码
        int statusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(statusCode,200);
        Reporter.log("状态码："+statusCode,true);
        Reporter.log("接口地址： "+loginUrl);
    }

    @Test(dataProvider = "get")
    public void getApi(String url) throws Exception{
        closeableHttpResponse = restClient.getApi(host+ url);
        int statusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(statusCode,200);
        Reporter.log("状态码："+statusCode,true);
        Reporter.log("接口地址： "+url);
    }

    @Test(dataProvider = "delete")
    public void deleteApi(String url) throws Exception{
        System.out.println(url);
        closeableHttpResponse = restClient.deleteApi(url);
        int statusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(statusCode,204);
        Reporter.log("状态码："+statusCode,true);
        Reporter.log("接口地址： "+url);
    }

    @BeforeClass
    public void endTest(){
        System.out.print("测试结束");
    }

}
