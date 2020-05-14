package com.leyou.cart.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: 登录拦截器
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/14
 */
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private JwtProperties jwtProperties;

    public LoginInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 定义一个线程域，存放登录用户
     */
    private static final ThreadLocal<UserInfo> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 查询Token
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());

        // 未登录，返回401
        if(StringUtils.isBlank(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        // 有token，查询用户信息
         try {
             // 解析成功，说明已经登录
             UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
             // 放入线程域
             USER_INFO_THREAD_LOCAL.set(userInfo);
             return true;
         } catch (Exception e) {
             // 抛出异常，证明未登录,返回401
             response.setStatus(HttpStatus.UNAUTHORIZED.value());
             return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        USER_INFO_THREAD_LOCAL.remove();
    }

    public static UserInfo getUserInfo() {
        return USER_INFO_THREAD_LOCAL.get();
    }
}
