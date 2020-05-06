package com.leyou.user.service;

import com.leyou.LyUserService;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.common.util.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Title: 用户业务层
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/06
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;
    
    private static final String KEY_PREFIX = "user:code:phone:";

    public Boolean checkData(String data, Integer type) {
        User record = new User();
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                throw new LyException(ExceptionEnums.INVALID_CHECK_DATA_TYPE);
        }
        return userMapper.selectCount(record) == 0;
    }

    public void sendVerifyCode(String phone) {
        String code = NumberUtils.generateCode(6);
        try {
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            log.info("发送验证码。phone：{}，code：{}", phone, code);
            amqpTemplate.convertAndSend("leyou.sms.exchange", "sms.verify.code", msg);
            //将验证码存入redis
            redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("发送短信失败。phone：{}，code：{}", phone, code);
            throw new LyException(ExceptionEnums.SEND_VERIFY_CODE_FAILED);
        }
    }
}
