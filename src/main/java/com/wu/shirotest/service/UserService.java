package com.wu.shirotest.service;


import com.wu.shirotest.pojo.User;

import java.util.List;

public interface UserService {

    User findUserByUserNameAndPassword(String userName);

    User findUserById(Integer id);

    List<User> findUserInfo();

    int updatePermission(Integer id,String permission);
}
