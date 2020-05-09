package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Title: 商品服务启动类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/09/24
 */
@SpringCloudApplication
@MapperScan("com.leyou.item.mapper")
public class LyItemService {
    public static void main(String[] args) {
        SpringApplication.run(LyItemService.class, args);
    }
}
