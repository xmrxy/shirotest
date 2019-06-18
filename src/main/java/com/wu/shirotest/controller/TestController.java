package com.wu.shirotest.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dialog")
public class TestController {



    @RequestMapping(value = "/dialogPage")
    public String DialogHtml(){
        System.out.println("22222");
        return "dialog";
    }
}
