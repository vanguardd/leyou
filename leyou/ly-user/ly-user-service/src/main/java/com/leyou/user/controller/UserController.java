package com.leyou.user.controller;

import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
