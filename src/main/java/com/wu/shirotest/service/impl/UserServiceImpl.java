package com.wu.shirotest.service.impl;

import cn.hutool.http.useragent.UserAgentUtil;
import com.wu.shirotest.pojo.User;
import com.wu.shirotest.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static User jack=new User(1,"jack","111111","user:add");
    private static User tom=new User(2,"tom","111111","user:update");
    private static User wu=new User(3,"wu","111111","excel");
    private static   List<User> users=new ArrayList<User>();
     static {
         users.add(jack);
         users.add(tom);
         users.add(wu);
     }

     public static List<User> getUsers(){
        return users;
     }

    @Override
    public User findUserByUserNameAndPassword(String userName) {
        for (User user:users){
            if (userName.equals(user.getUserName())){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findUserById(Integer id) {
        for (User user:users){
            if (id.toString().equals(user.getId().toString())){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findUserInfo() {
        return users;
    }

    @Override
    public int updatePermission(Integer id, String permission) {
         int i=-1;
         for(User user:users){
             if (id.toString().equals(user.getId().toString())){
                 i++;
                 user.setHead(permission);
                 users.add(i,user);
                 return 1;
             }
         }
        return 0;
    }
}
