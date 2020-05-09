package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Title: 用户API
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/09
 */
public interface UserApi {

    /**
     * 根据用户名和密码查询用户信息
     * @param username
     * @param password
     * @return com.leyou.user.pojo.User
     * @author vanguard
     * @date 20/5/9 20:38
     */
    @GetMapping("query")
    User queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password);
}
