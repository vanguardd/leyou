package com.leyou.service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.client.UserClient;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.order.config.JwtProperties;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @Title: 授权业务
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/08
 */
@Slf4j
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    public String authentication(String username, String password) {
        try {
            // 调用用户微服务查询用户信息
            User user = userClient.queryUser(username, password);
            if(user == null) {
                throw new LyException(ExceptionEnums.USERNAME_OR_PASSWORD_ERROR);
            }
            // 查询到用户信息，生成token
            return JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        } catch (Exception e) {
            log.error("【授权服务】登录授权异常, {}", e.getMessage());
            throw new LyException(ExceptionEnums.USER_LOGIN_ERROR);
        }

    }
}
