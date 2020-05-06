package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Title: 用户接口
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/06
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 实现用户数据的校验，主要包括对：手机号、用户名的唯一性校验。
     * @param data 校验的数据
     * @param type 类型
     * @return org.springframework.http.ResponseEntity<java.lang.Boolean>
     * @author vanguard
     * @date 20/5/6 16:47
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        Boolean isUse = userService.checkData(data, type);
        return ResponseEntity.ok(isUse);
    }

    /**
     * 发送验证码
     * @param phone 手机号
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author vanguard
     * @date 20/5/6 17:28
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(String phone) {
        userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 用户注册
     * @param user 注册用户信息
     * @param code 验证码
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author vanguard
     * @date 20/5/6 20:36
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code) {
        userService.register(user, code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("query")
    public ResponseEntity<User> query(@RequestParam("username") String username,
                                      @RequestParam("password") String password) {
        User user = userService.queryUser(username, password);
        return ResponseEntity.ok(user);
    }
}
