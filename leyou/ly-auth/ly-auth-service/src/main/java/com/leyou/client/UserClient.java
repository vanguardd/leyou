package com.leyou.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Title: 用户服务client
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/09
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}
