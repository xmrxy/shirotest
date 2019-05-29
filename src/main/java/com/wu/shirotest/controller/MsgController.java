package com.wu.shirotest.controller;

import com.wu.shirotest.util.SmsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/msg")
public class MsgController {


    @RequestMapping(value = "/sendMsg")
    public String sendMsg(@RequestParam("toNum") String to,
                        @RequestParam("content") String content,
                        @RequestParam("time") String time) {
        SmsUtil.sendSms(to, content, time);
        return "success";

    }
}
