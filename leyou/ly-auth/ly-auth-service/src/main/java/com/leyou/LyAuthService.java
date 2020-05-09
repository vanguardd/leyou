package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: 授权服务启动类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/08
 */
@EnableFeignClients
@SpringCloudApplication
public class LyAuthService {

    public static void main(String[] args) {
        SpringApplication.run(LyAuthService.class, args);
    }
}
