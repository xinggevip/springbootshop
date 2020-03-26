package com.qiangssvip.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {
    @RabbitHandler
    public void process(String msg) {
        log.info("msg = {}", msg);
    }
}
