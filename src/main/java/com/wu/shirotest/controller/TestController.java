package com.wu.shirotest.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ytx.org.apache.http.impl.client.DefaultHttpClient;

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


}
