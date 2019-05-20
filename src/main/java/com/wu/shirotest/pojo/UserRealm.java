package com.wu.shirotest.pojo;

import com.wu.shirotest.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 处理授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("处理授权逻辑");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User userById = userService.findUserById(user.getId());
        info.addStringPermission(userById.getHead());
        return info;
    }

    /**
     * 处理认证逻辑
     * @param arg
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg) throws AuthenticationException {
        System.out.println("处理认证逻辑");
        UsernamePasswordToken token= (UsernamePasswordToken) arg;
        User user = userService.findUserByUserNameAndPassword(token.getUsername());
        //判断用户名
        if (user==null){
            //用户名不正确
            return null;//Shiro底层会抛出用户名错误的异常
        }

        //判断密码
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), "");
        return info;
    }
}
