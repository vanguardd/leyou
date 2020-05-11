package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: 购物车服务启动类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/11
 */
@EnableFeignClients
@SpringCloudApplication
public class LyCartService {
    public static void main(String[] args) {
        SpringApplication.run(LyCartService.class, args);
    }
}
