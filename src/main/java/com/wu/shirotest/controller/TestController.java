package com.wu.shirotest.controller;


import net.sf.json.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.http.util.EntityUtils;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping(value = "/dialog")
public class TestController {



    @RequestMapping(value = "/dialogPage")
    public String DialogHtml(){
        System.out.println("22222");
        return "dialog";
    }


    @RequestMapping(value = "/testJson")
    @ResponseBody
    public String testReturnJson(@RequestParam(value = "param1")String param1,
                                 @RequestParam(value = "param2")String param2){
        if ("123".equals(param1)&&"321".equals(param2)){
            return "{'resultCode':'200','userName':'TestUserName','password':'TestPassword'}";
        }else {
            return "{'resultCode':'400'}";
        }
    }

    @RequestMapping(value = "/test1")
    @ResponseBody
    public String test1(@RequestBody String json){
        System.out.println("json:==="+json);
//        return "{'jsonDate':'test'}";
        System.out.println("进入test1接口");
        return "{'result':'0'}";
    }


    @RequestMapping(value = "/testReturn")
    @ResponseBody
    public ModelAndView test3(ModelAndView modelAndView){
        modelAndView.setViewName("testreture");
        return modelAndView;
    }




    @RequestMapping(value = "/test2")
    @ResponseBody
    public String test2(){
        System.out.println("进入test2接口");
            //参数设置
//            String url = "http://132.108.207.27/weixin_v3/w?urlToken=wxmanager";
            String url = "http://172.20.36.50/weixin_v3/w?urlToken=wxmanager";
            String strJson="{'type':'req','opt':'connect_to_kf','data':{'admin_id':'weixinxmt','admin_pass':'948168ad29b83671fd18289f9a7cd389','service_no':'gh_d2d9c60e2b07','user_no':'oBBmBjilGVX7DNGjUYHfR-YLpsRk','in_city_id':'200','channel_id':'zwzx01','content':'','expire':'测试，请忽略'}}";
            // 创建Httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //设置请求超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(100000).setConnectionRequestTimeout(100000).setSocketTimeout(100000).build();
            CloseableHttpResponse response=null;
            String result=null;
            try {
                HttpPost httpPost = new HttpPost(url);// 创建Http Post请求
                httpPost.setConfig(requestConfig);
                httpPost.addHeader("Content-type", "application/json");
                // 创建请求内容 ，发送json数据需要设置contentType
                StringEntity entity = new StringEntity(strJson);
                httpPost.setEntity(entity);
                // 执行http请求
                try
                {
                    response = httpClient.execute(httpPost);
                }catch(Exception e){
                    e.printStackTrace();
                }
                if (response.getStatusLine().getStatusCode() == 200) {
                    String  resultString = EntityUtils.toString(response.getEntity(),"utf-8");//结果变成String
                    JSONObject jsonObject = JSONObject.fromObject(resultString);//String变成JSONObject
                    result=jsonObject.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
    }




}
