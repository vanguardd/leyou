package com.leyou.user.service;

import com.leyou.LyUserService;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.common.util.CodecUtils;
import com.leyou.common.util.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    /**
     * 校验数据是否可用
     * @param data 校验的数据
     * @param type 类型 1：用户名，2：手机号
     * @return java.lang.Boolean true：可用，false：不可用
     * @author vanguard
     * @date 20/5/6 20:37
     */
    public Boolean checkData(String data, Integer type) {
        User record = new User();
        // 判断校验类型
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

    /**
     * 发送验证码
     * @param phone 手机号
     * @return void
     * @author vanguard
     * @date 20/5/6 20:39
     */
    public void sendVerifyCode(String phone) {
        // 生成六位的验证码
        String code = NumberUtils.generateCode(6);
        try {
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            log.info("发送验证码。phone：{}，code：{}", phone, code);
            // 发送消息，发送验证码
            //amqpTemplate.convertAndSend("leyou.sms.exchange", "sms.verify.code", msg);

            // 将验证码存入redis，并设置过期时间5分钟
            redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("发送短信失败。phone：{}，code：{}", phone, code);
            throw new LyException(ExceptionEnums.SEND_VERIFY_CODE_FAILED);
        }
    }

    /**
     * 用户注册
     * @param user 注册的用户
     * @param code 验证码
     * @return void
     * @author vanguard
     * @date 20/5/6 20:41
     */
    public void register(User user, String code) {
        // 1. 校验短信验证码
        String cacheCode = redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        if(!StringUtils.equals(code, cacheCode)) {
            throw new LyException(ExceptionEnums.USER_REGISTER_VERIFY_CODE_ERR);
        }

        // 2. 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        // 3. 对密码进行加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        user.setCreated(new Date());

        // 4. 写入数据库
        boolean isSuccess = userMapper.insertSelective(user) == 1;

        // 5. 保存成功，删除redis中的验证码
        if(isSuccess) {
            redisTemplate.delete(KEY_PREFIX + user.getPhone());
        }
    }

    /**
     * 根据用户名和密码查询用户信息
     * @param username 用户名
     * @param password 密码
     * @return com.leyou.user.pojo.User
     * @author vanguard
     * @date 20/5/6 21:12
     */
    public User queryUser(String username, String password) {
        // 根据用户名查询用户信息
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);

        // 校验密码
        if(!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            throw new LyException(ExceptionEnums.USERNAME_OR_PASSWORD_ERROR);
        }

        // 用户名和密码都正确
        return user;
    }
}
