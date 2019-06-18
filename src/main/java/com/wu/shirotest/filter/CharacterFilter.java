package com.wu.shirotest.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/doLogin",filterName = "CharacterFilter")
public class CharacterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String userName =request.getParameter("userName");
        String password =request.getParameter("password");
        password.replace("'","");
        password.replace("or","");
        password.trim();
        System.out.println("username:"+userName);
        System.out.println("password:"+password);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
