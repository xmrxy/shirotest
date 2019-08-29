package com.wu.shirotest.controller;

import com.wu.shirotest.util.JsonUtil;
import com.wu.shirotest.util.VCodeUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.util.HashMap;
import java.util.Map;

@Controller
public class VCodeController {

    @RequestMapping("/VCodePage")
    public String vCodePage(){
        return "vcode";
    }

    @RequestMapping("/checkCode")
    @ResponseBody
    public String checkCode(@RequestParam("code") String code,HttpServletRequest req){
        String json="";
        Map map=new HashMap();
        if (code==null||"".equals(code)){
            map.put("msg","loss");
            json = JsonUtil.getJson(map);
            return json;
        }
        String x_code=code.toLowerCase();
        String v_code =req.getSession().getAttribute("code").toString().toLowerCase();

        if (x_code.equals(v_code)) {
            map.put("msg","success");
            return JSONObject.fromObject(map).toString();
        }else{
            map.put("msg","loss");
            json = JsonUtil.getJson(map);
            return json;
        }


    }

    @RequestMapping("/getVCode")
    public void getTowCode(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<String, Object> map = VCodeUtil.generateCodeAndPic();

            // 将四位数字的验证码保存到Session中。
            HttpSession session = req.getSession();
            session.removeAttribute("code");
            session.setAttribute("code", map.get("code").toString());

            // 禁止图像缓存。
            resp.setHeader("Pragma", "no-cache");
            resp.setHeader("Cache-Control", "no-cache");
            resp.setDateHeader("Expires", -1);

            resp.setContentType("image/jpeg");
            // 将图像输出到Servlet输出流中。
            ServletOutputStream sos;
            sos = resp.getOutputStream();
            ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", sos);
            sos.close();
            System.out.println("验证码的值为：" + map.get("code"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
