package com.wu.shirotest.util;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class WeChatUtil {
    //URL验证时使用的token
    public static final String TOKEN = "wxts";
    //appid
    public static final String APPID = "wxf29f1352f05b51d0";
    //secret
    public static final String SECRET = "d7a67aa8ee9a95042fcb6b809c8de080";
    //创建菜单接口地址
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //获取access_token的接口地址
    public static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //缓存的access_token
    private static String accessToken;
    //access_token的失效时间
    private static long expiresTime;

    /**
     * 获取accessToken
     * @return
     */
    public static String getAccessToken(){
        //判断accessToken是否已经过期，如果过期需要重新获取
        if(accessToken==null||expiresTime < new Date().getTime()){
            //发起请求获取accessToken
            String result = HttpUtil.get(GET_ACCESSTOKEN_URL.replace("APPID", APPID).replace("APPSECRET", SECRET));
            //把json字符串转换为json对象
            JSONObject json=null;
            try {
                json=JsonUtil.getMapper().readValue(result,JSONObject.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //缓存accessToken
            accessToken = (String) json.get("access_token");
            //设置accessToken的失效时间
            long expires_in = json.getLong("expires_in");
            //失效时间 = 当前时间 + 有效期(提前一分钟)
            expiresTime = new Date().getTime()+ (expires_in-60) * 1000;
        }
        return accessToken;
    }

    /**
     * 创建自定义菜单
     * @param menu
     */
    public static void createMenu(String menu){
        String result = HttpUtil.post(CREATE_MENU_URL.replace("ACCESS_TOKEN", getAccessToken()),menu);
        System.out.println(result);
    }

}
