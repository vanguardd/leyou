package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Title: 商品详情服务启动类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/04/22
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class LyGoodsWeb {
    public static void main(String[] args) {
        SpringApplication.run(LyGoodsWeb.class, args);
    }
}
