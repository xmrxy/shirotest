package com.wu.shirotest.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wu.shirotest.pojo.GithubPojo;
import com.wu.shirotest.util.JsonUtil;
import com.wu.shirotest.util.UrlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/github")
public class GithubContrller {

    @Resource
    private GithubPojo github;

    @RequestMapping(value = "/githubLoginCallback")
    public ModelAndView githubCallBack(@RequestParam(value = "code",required = true) String code,ModelAndView modelAndView){
            Map<String,Object> map=new HashMap<>();
            map.put("client_id",github.getClientId());
            map.put("client_secret",github.getClientSecret());
            map.put("code",code);
        String accessTokenUrl = "https://github.com/login/oauth/access_token?client_id=" + map.get("client_id") + "&client_secret=" +map.get("client_secret") + "&code=" + code;
        String respAccessTokenJson = UrlUtils.loadURLByJson(accessTokenUrl);
        System.out.println("token====="+respAccessTokenJson);
        map.clear();
        try {
            map = JsonUtil.getMapper().readValue(respAccessTokenJson, Map.class);
            String getTokenUrl = "https://api.github.com/user?access_token=" + map.get("access_token");
            String token = UrlUtils.loadURL(getTokenUrl);
            map = JsonUtil.getMapper().readValue(token, Map.class);
            for (Object o:map.keySet()){
                System.out.println(map.get(o));
            }
            modelAndView.setViewName("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }



}
