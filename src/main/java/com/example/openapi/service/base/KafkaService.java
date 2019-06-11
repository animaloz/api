package com.example.openapi.service.base;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/3/30
 */
@Component
public class KafkaService {

//    @KafkaListener(topics = {"app_log"})
//    public void receive(String message){
//        System.out.println("kafka app_log--消费消息:" + message);
//    }
}