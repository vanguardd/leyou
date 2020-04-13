package com.leyou.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Title:
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/09/19
 */
@EnableZuulProxy
@SpringCloudApplication
public class LyGateway {

    public static void main(String[] args) {
        SpringApplication.run(LyGateway.class, args);
    }
}
