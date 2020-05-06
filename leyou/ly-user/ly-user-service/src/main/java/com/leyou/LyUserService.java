package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Title: 用户服务启动类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/06
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.leyou.user.mapper")
public class LyUserService {
    public static void main(String[] args) {
        SpringApplication.run(LyUserService.class, args);
    }
}
