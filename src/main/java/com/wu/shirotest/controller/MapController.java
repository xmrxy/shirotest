package com.wu.shirotest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/map")
public class MapController {

    @RequestMapping(value = "/mapHtml")
    public String mapHtml(){
        return "map";
    }
}
