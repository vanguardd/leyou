package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @title: 搜索服务启动类
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
@EnableFeignClients
@SpringCloudApplication
public class LySearchService {
    public static void main(String[] args) {
        SpringApplication.run(LySearchService.class);
    }
}
