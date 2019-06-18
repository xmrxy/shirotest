package com.wu.shirotest.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.wu.shirotest.util.JsonUtil;
import com.wu.shirotest.util.WeChatUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@Controller
public class WxController {

    public static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    @RequestMapping(value = "/wx_send",method = RequestMethod.GET)
    @ResponseBody
    public String wx_send(@RequestParam("signature") String signature,
                          @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce,
                          @RequestParam("echostr") String echostr){
        //1. 将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = {timestamp,nonce,"wxts"};
        Arrays.sort(arr);
        //2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder sb = new StringBuilder();
        for (String temp : arr) {
            sb.append(temp);
        }
        //3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if( DigestUtils.sha1Hex(sb.toString()).equals(signature)){
            //接入成功
            return echostr;
        }
        //接入失败
        return null;
    }


    /**
     * 发送模板
     *
     */
    @RequestMapping(value = "/send")
    public  String sendTemplate(@RequestParam(value = "openId",required = false)String openId){
        if(openId==null||"".equals(openId)){
            openId="oFE0_1B5XE2MgO2pi6O9VBYvXFZo";
        }
        String data="{\n" +
                "\t\"touser\": \""+openId+"\",\n" +
                "\t\"template_id\": \"AqKlGWBrrJgiXAzzjSqZ08dTJI1LfWvmDhH_BGa1xVI\",\n" +
                "    \"url\":\"114.116.29.29/result\",\n" +
                "\t\"data\": {\n" +
                "\t\t\"context\": {\n" +
                "\t\t\t\"value\": \"恭喜你,购买成功！\",\n" +
                "\t\t\t\"color\": \"#173177\"\n" +
                "\t\t},\n" +
                "\t\t\"name\": {\n" +
                "\t\t\t\"value\": \"保时捷 Macan S\",\n" +
                "\t\t\t\"color\": \"#173177\"\n" +
                "\t\t},\n" +
                "\t\t\"price\": {\n" +
                "\t\t\t\"value\": \"59.8万人民币\",\n" +
                "\t\t\t\"color\": \"#173177\"\n" +
                "\t\t},\n" +
                "\t\t\"time\": {\n" +
                "\t\t\t\"value\": \"2014年9月22日\",\n" +
                "\t\t\t\"color\": \"#173177\"\n" +
                "\t\t},\n" +
                "\t\t\"success\": {\n" +
                "\t\t\t\"value\": \"保时捷祝贺您用车愉快\",\n" +
                "\t\t\t\"color\": \"#173177\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        String result = HttpUtil.post(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN",WeChatUtil.getAccessToken()),data);
        JSONObject json=null;
        try {
            json=JsonUtil.getMapper().readValue(result,JSONObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("errmsg==="+json.get("errmsg"));
        if (json.get("errmsg").equals("ok")){
            return  "success";
        }else {
            return "lose";
        }
    }



}
