package com.wu.shirotest.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.wu.shirotest.pojo.User;
import com.wu.shirotest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/excel")
public class ReadExcel {

    @Resource
    private UserService userService;

    /**
     * 读取excel方法
     * @return
     */
    @RequestMapping(value = "/ReadExcel")
    public String readExcel() {
        int i = 0;
        ExcelReader excel = ExcelUtil.getReader("C:\\Users\\Administrator\\Desktop\\test.xlsx");
        List<Map<String, Object>> excelMap = excel.readAll();
        for (Map<String, Object> map : excelMap) {
            i++;
            System.out.println("第" + i + "个人的信息：");
            System.out.println("姓名:" + map.get("姓名").toString().trim());
            System.out.println("年龄:" + map.get("年龄").toString().trim());
        }
        return "success";
    }


    /**
     * 从数据库导出excel表格方法
     * @param response
     * @return
     */
    @RequestMapping(value = "/writeExcel")
    @ResponseBody
    public String writeExcel(HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
//        List<User> users = userService.findUserInfo();

        User user = new User(2,"zhangsan","张三","111111","111111","123@aa.com","user:add");
        User user1 = new User(3,"list","李四","111111","111111","123@aa.com","user:update");
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);

        User userTitle=new User("编号","用户名","真实姓名","密码","电话","邮箱","权限");
        users.add(0,userTitle);
        excelWriter.passCurrentRow();
        excelWriter.merge(6, "人员信息");
        excelWriter.write(users);
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=index.xlsx");
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                excelWriter.flush(outputStream);
                outputStream.close();
                excelWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "success";
    }




}
