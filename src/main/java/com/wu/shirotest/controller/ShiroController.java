package com.wu.shirotest.controller;

import cn.hutool.captcha.CircleCaptcha;
import com.wu.shirotest.pojo.User;
import com.wu.shirotest.service.UserService;
import com.wu.shirotest.util.JsonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class ShiroController {

    @Resource
    private UserService userService;



    @RequestMapping(value = "/index")
    public String indexPage(Model model) {
        model.addAttribute("name", "测试thymeleaf");
        return "index";
    }
    @RequestMapping(value = "/result")
    public String resultPage(Model model) {
        return "result";
    }

    @RequestMapping(value = "/add")
    public String addPage() {
        return "add";
    }

    @RequestMapping(value = "/update")
    public String updatePage() {
        return "update";
    }

    @RequestMapping(value = "/login")
    public  String  loginPage(HttpServletResponse response) {
        return "login";
    }



    @RequestMapping(value = "/givepermission")
    public String givepermission(HttpServletRequest request, Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("password", user.getPassword());
        return "givepermission";
    }

    @RequestMapping(value = "/unAuthor")
    public String unAuthorPage() {
        return "unAuthor";
    }

    @RequestMapping(value = "/doLogin")
    public String doLogin(User user, Model model, HttpServletRequest request, String yanNum) {
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户名密码
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            subject.login(token);
        } catch (Exception e) {
            model.addAttribute("msg", "用户名或密码错误");
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("password", user.getPassword());
            //用户名或密码错误
            return "login";
        }
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60 * 60);
        return "redirect:/index";
    }

    @RequestMapping(value = "/logOut")
    public String logOut() {
        Subject currentSubject = SecurityUtils.getSubject();
        currentSubject.logout();
        return "redirect:/login";
    }

    @RequestMapping(value = "/updatePermission")
    @ResponseBody
    public String updatePermission(@Param("permission") String permission) {
        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> map = new HashedMap();
        User user = (User) subject.getPrincipal();
        System.out.println("user====" + user.toString());
        int i = userService.updatePermission(user.getId(), permission);
        if (i > 0) {
            map.put("result", "success");
            return JsonUtil.getJson(map);
        } else {
            map.put("result", "error");
            return JsonUtil.getJson(map);
        }
    }

    @RequestMapping(value = "/sendSMS")
    public void sendSMS(){

    }



}
