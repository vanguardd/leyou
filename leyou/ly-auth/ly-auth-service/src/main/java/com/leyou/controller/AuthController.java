package com.leyou.controller;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.CookieUtils;
import com.leyou.config.JwtProperties;
import com.leyou.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: 授权接口
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/08
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录授权
     * @param username 用户名
     * @param password 密码
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author vanguard
     * @date 20/5/8 19:49
     */
    @PostMapping("login")
    public ResponseEntity<Void> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response
    ) {
        // 登录校验授权
        String token = authService.authentication(username, password);
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getCookieMaxAge(), null, true);
        return ResponseEntity.ok().build();
    }

    /**
     * 验证用户信息并刷新token
     * @param token cookie中的token
     * @return org.springframework.http.ResponseEntity<com.leyou.auth.entity.UserInfo>
     * @author vanguard
     * @date 20/5/9 21:50
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verifyUser(@CookieValue(value = "${leyou.jwt.cookieName}") String token,
                                               HttpServletRequest request, HttpServletResponse response) {
        try {
            // 从token中验证用户信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            // 验证成功后，刷新重新生成token
            String refreshToken = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), refreshToken, jwtProperties.getCookieMaxAge(), null, true);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            throw new LyException(ExceptionEnums.USER_VERIFY_ERROR);
        }
    }
}
