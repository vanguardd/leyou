package com.leyou.sms.listener;

import com.leyou.common.utils.JsonUtils;
import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @Title: 短信消息监听类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/05
 */
@Slf4j
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties prop;

    /**
     * 发送短信验证码
     * @param msg
     * @return void
     * @author vanguard
     * @date 20/5/5 22:07
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.sms.verify.code.queue", durable = "true"),
            exchange = @Exchange(value = "leyou.sms.exchange",
                    ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"}))
    public void listenSms(Map<String, String> msg) {
        if(CollectionUtils.isEmpty(msg)) {
            return;
        }
        String phone = msg.remove("phone");
        if (StringUtils.isBlank(phone)) {
            // 放弃处理
            return;
        }
        // 发送消息
        smsUtils.sendSms(phone, JsonUtils.toString(msg), prop.getSignName(), prop.getVerifyCodeTemplate());

    }
}
