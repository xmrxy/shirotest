package com.wu.shirotest.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时器
 */
//@Component
public class ScheduledUtil {

    @Scheduled(cron = "0/2 * * * * ?")
    public void test(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        System.out.println(date);
    }
}
