package com.wu.shirotest.shiroConfig;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.wu.shirotest.pojo.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 *
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         *  设置权限过滤器
         *  anon:无需认证
         *  authc:必须认证
         *  user：如果使用rememberMe的功能可以直接访问
         *  perms:该资源必须获得资源授权才能访问
         *  role:该资源必须获得角色权限才能访问
         */
        Map<String,String> filterMap=new LinkedHashMap<>();
//        认证
        filterMap.put("/index","anon");
        filterMap.put("/doLogin","anon");
        filterMap.put("/testJson","anon");
        filterMap.put("/showPicture","anon");
        filterMap.put("/getVCode","anon");
        filterMap.put("/VCodePage","anon");
        filterMap.put("/checkCode","anon");
//        filterMap.put("/logOut","anon");
//        filterMap.put("/static/*","anon");
//        filterMap.put("/excel/ReadExcel","anon");
//        filterMap.put("/excel/writeExcel","anon");
//        filterMap.put("/wx_send","anon");
//        filterMap.put("/send","anon");
        filterMap.put("/result","anon");
        filterMap.put("/github/*","anon");
        filterMap.put("/dialogPage","anon");
//        授权
        filterMap.put("/excel/*","perms[excel]");
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");
        filterMap.put("/msg","perms[sendMsg]");

        filterMap.put("/*","authc");
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuthor");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean(name = "userRealm")
    public UserRealm getUserRealm(){
        return  new UserRealm();
    }

    //结合thymeleaf-extras-Shiro模板标签使用
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
