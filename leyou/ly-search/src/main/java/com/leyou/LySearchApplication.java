package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @title: 搜索服务启动类
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class LySearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LySearchApplication.class);
    }
}
