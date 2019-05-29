package com.wu.shirotest.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wu.shirotest.pojo.GithubPojo;
import com.wu.shirotest.pojo.User;
import com.wu.shirotest.service.impl.UserServiceImpl;
import com.wu.shirotest.util.JsonUtil;
import com.wu.shirotest.util.UrlUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
            String userInfo = UrlUtils.loadURL(getTokenUrl);
            map = JsonUtil.getMapper().readValue(userInfo, Map.class);
            Iterator<String> iterator = map.keySet().iterator();
            for (Object o:map.keySet()){
                System.out.println("key:"+o);
                System.out.println("values:"+map.get(o));
            }
            String userName = map.get("login").toString();
            String password = map.get("id").toString();
            User github=new User(4,userName,password,"user:add");
            UserServiceImpl.getUsers().add(github);

            Subject subject=SecurityUtils.getSubject();
            UsernamePasswordToken token=new UsernamePasswordToken(userName,password);
            try {
                subject.login(token);
            } catch (Exception e) {
                modelAndView.addObject("msg", "用户名或密码错误");
                modelAndView.addObject("userName", userName);
                modelAndView.addObject("password", password);
                modelAndView.setViewName("login");
                //用户名或密码错误
                return modelAndView;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/index");

    }



}
