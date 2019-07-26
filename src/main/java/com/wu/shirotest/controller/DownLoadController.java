package com.wu.shirotest.controller;

import cn.hutool.core.io.FileUtil;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class DownLoadController {

    @RequestMapping(value = "/downLoadTXT")
    public void downLoadTXT(HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        File file=new File("D://test.txt");
        if (file.exists()) {

            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=text.txt");

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try{
                outputStream = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while(i!=-1){
                outputStream.write(buffer,0,i);
                i=bis.read(buffer);
            }
            System.out.println("下载成功");
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if (bis!=null){
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis!=null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("下载失败");
        }
    }




}
