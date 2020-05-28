package com.leyou.sms;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


/**
 * @Title:
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSend() throws InterruptedException {
        Map<String, String> msg = new HashMap<>();
        msg.put("phone", "17691229635");
        msg.put("code", "253897");
        amqpTemplate.convertAndSend("leyou.sms.exchange", "sms.verify.code", msg);

        Thread.sleep(10000L);
    }
}