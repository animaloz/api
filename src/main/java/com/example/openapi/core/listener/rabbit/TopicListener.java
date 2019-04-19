package com.example.openapi.core.listener.rabbit;

import com.alibaba.fastjson.JSON;
import com.example.openapi.model.vo.in.UserInVO;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/4/3
 */
@Component
@RabbitListener(bindings = {@QueueBinding(value = @Queue(value = "topic.messages"), exchange = @Exchange(name ="queueMessages"))})
public class TopicListener {
    @RabbitHandler(isDefault = true)
    public void byteBody(@Payload byte[] body) {
        System.out.println("Received byte for body " + new String(body));
    }
    @RabbitHandler
    public Message message(Message message) {
        System.out.println("Received Message message" + JSON.toJSONString(message));
        return message;
    }
    @RabbitHandler
    public String byteBodyStringJob(@Payload String body) {
        System.out.println("Received String for body " + body);
        return "hello";
    }

    @RabbitHandler
    public UserInVO entityBody(@Payload UserInVO userInVO) {
        System.out.println("Received UserInVO for body " + userInVO.toString());
        return userInVO;
    }
//    @RabbitHandler
//    public void entityBody(@Payload Map map) {
//        System.out.println("Received UserInVO for body " + JSON.toJSONString(map));
//    }

    @RabbitHandler
    public void entityBody(@Payload Map map, Channel channel) throws IOException {
        System.out.println("Received Channel for body " + JSON.toJSONString(map));
        System.out.println("Received Channel for body " + JSON.toJSONString(map));
//        channel.basicAck(channel.getNextPublishSeqNo(), true);
    }


    public static void main(String[] args) {
        int size = 2 << 16;
        List<String> list = Lists.newArrayListWithCapacity(size);
        for (int i = 0; i < size; i++) {
            list.add(String.valueOf(i));
        }
        StopWatch stopWatch = new StopWatch("12121");
        stopWatch.setKeepTaskList(true);
        stopWatch.start();
        Map map = new LinkedHashMap(size);
        for (String s : list) {
            if("1000".compareTo(s) < 0){
                UserInVO lombokVo = UserInVO.builder().address(s + "map").build();
                map.put(lombokVo.hashCode(), lombokVo.getAddress());
            }
        }
        System.out.println(stopWatch.getTaskInfo()[0].getTimeMillis());
        list.stream().filter(s -> "1000".compareTo(s) < 0).map(s -> UserInVO.builder().address(s + "map").build()).collect(Collectors.toMap(Object::hashCode, UserInVO::getAddress, (s, s2) -> {
            System.out.println(s);
            System.out.println(s2);
            return s2;
        }, () -> new LinkedHashMap(size)));
        System.out.println(stopWatch.getTotalTimeSeconds());
        stopWatch.stop();
    }
}
