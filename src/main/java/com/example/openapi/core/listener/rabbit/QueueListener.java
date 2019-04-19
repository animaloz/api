package com.example.openapi.core.listener.rabbit;

import com.example.openapi.core.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/4/3
 */
@Component
@RabbitListener(queues = {RabbitMqConfig.QUEUE_HELLO,RabbitMqConfig.QUEUE_USER})
public class QueueListener {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver1  : " + hello);
    }
}
