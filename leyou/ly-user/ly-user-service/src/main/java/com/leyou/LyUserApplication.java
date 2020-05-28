package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Title: 用户服务启动类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/06
 */
@SpringCloudApplication
@MapperScan("com.leyou.user.mapper")
public class LyUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyUserApplication.class, args);
    }
}
