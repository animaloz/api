package com.example.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.example.openapi.core.config.RabbitMqConfig;
import com.example.openapi.model.vo.in.UserInVO;
import com.example.openapi.model.vo.out.UserOutVO;
import com.example.openapi.service.TestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.remote.service.HelloService;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/3/30
 */
@RestController
@RequestMapping("/test")
public class TestController {

//    @Reference
//    private HelloService helloService;

    @Autowired
    private TestService testService;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostMapping("send")
    public void send() {
        String message = UUID.randomUUID().toString();
        ListenableFuture future = kafkaTemplate.send("app_log", message);
        future.addCallback(o -> System.out.println("kafka send-消息发送成功：" + message), throwable -> System.out.println(
                "kafka 消息发送失败：" + message));
    }

    @PostMapping("sendRabbit")
    public void sendRabbit() {
        String sendMsg = "hello1 " + new Date();
        this.rabbitTemplate.send("exchange", "topic.#", new Message(sendMsg.getBytes(), new MessageProperties()));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Message exchange = this.rabbitTemplate.sendAndReceive("exchange", "topic.#", new Message(sendMsg.getBytes(), new MessageProperties()));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("*********" + JSON.toJSONString(exchange));
    }

    @PostMapping("sendRabbitQueue")
    public void sendRabbitQueue() {
        String sendMsg = "hello1 " + new Date();
        System.out.println("sendRabbitQueue : " + sendMsg);
        this.rabbitTemplate.convertAndSend(RabbitMqConfig.QUEUE_HELLO, sendMsg);
    }

    @PostMapping("sendRabbitTopic")
    public void sendRabbitTopic() {
        String sendMsg = "helloWorld";
        System.out.println("sendRabbitTopic : " + sendMsg);
        this.rabbitTemplate.convertAndSend("exchange", "topic.#", sendMsg);
        System.out.println("********* convertAndSend ***********");

        Object obj = this.rabbitTemplate.convertSendAndReceive("exchange", "topic.#", sendMsg);
        System.out.println("********* convertSendAndReceive ***********" + JSON.toJSONString(obj.toString()));
    }

    public static void main(String[] args) {
        UserInVO userInVO = UserInVO.builder().name("张三").build();
        Consumer<String> consumer = s -> userInVO.setAddress(s);
        consumer.accept("asfasdfa");
        System.out.println(userInVO.getAddress());
        Function<String, UserInVO> function = o -> UserInVO.builder().name(o).build();
        System.out.println(function.apply("zhangSan"));
        PropertyMapper map = PropertyMapper.get();
        map.from(userInVO::getName).whenNonNull().to(userInVO::setAddress);
        System.out.println(userInVO);
        Supplier<UserInVO> supplier = UserInVO::new;
        System.out.println(supplier.get());
        Predicate<UserInVO> predicate = Objects::isNull;
        System.out.println(predicate.test(userInVO));
        System.out.println(predicate.test(supplier.get()));
        System.out.println("***************************************");

        System.out.println(multiGetResult(
                Arrays.asList(
                        list -> list.stream().collect(Collectors.summarizingInt(x -> x)),
                        list -> list.stream().filter(x -> x < 50).sorted(Comparator.comparingInt(Integer::intValue)).collect(Collectors.toList()),
                        list -> list.stream().collect(Collectors.groupingBy(x -> x % 2 == 0 ? "even" : "odd")),
                        list -> list.stream().sorted().collect(Collectors.toList()),
                        list -> list.stream().map(Math::sqrt).sorted(Comparator.comparingDouble(Double::doubleValue)).collect(
                                Collectors.toMap(Double::doubleValue,
                                        y -> Math.pow(2, y.doubleValue()),
                                        (u, v) -> v,
                                        LinkedHashMap::new))),
                Arrays.asList(64, 49, 25, 16, 9, 4, 1, 81, 36, 64, 80)));

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(date);
        // 中文常见字
        String s = "你好";
        System.out.println("1. string length =" + s.length());
        System.out.println("1. string bytes length =" + s.getBytes().length);
        System.out.println("1. string char length =" + s.toCharArray().length);
        System.out.println("1. string codePoints length =" + s.codePoints().sum());
        System.out.println();
        // emojis
        s = "👦👩";
        System.out.println("2. string length =" + s.length());
        System.out.println("2. string bytes length =" + s.getBytes().length);
        System.out.println("2. string char length =" + s.toCharArray().length);
        System.out.println("2. string codePoints length =" + s.codePoints().sum());
        System.out.println();
        // 中文生僻字
        s = "𡃁妹𡃁妹";
        System.out.println("3. string length =" + s.length());
        System.out.println("3. string bytes length =" + s.getBytes().length);
        System.out.println("3. string char length =" + s.toCharArray().length);
        System.out.println("3. string codePoints length =" + s.codePoints().sum());
        System.out.println();

        System.out.println(3 / 10 * 10);
    }

    public static <T, R> List<R> multiGetResult(List<Function<List<T>, R>> functions, List<T> list) {
        return functions.stream().map(f -> f.apply(list)).collect(Collectors.toList());
    }

    @PostMapping("sendRabbitTopic2")
    public void sendRabbitTopic2() throws JsonProcessingException {
        UserInVO userInVO = UserInVO.builder().name("张三").build();
        Consumer<String> consumer = s -> userInVO.setAddress(s);
        consumer.accept("asfasdfa");
        System.out.println(userInVO.getAddress());
        Function<String, UserInVO> function = o -> UserInVO.builder().name(o).build();
        System.out.println(function.apply("zhangSan"));
        PropertyMapper map = PropertyMapper.get();

        map.from(userInVO.getName()).whenNonNull().to(userInVO::setAddress);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(userInVO);
        System.out.println(json);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(json.getBytes(), messageProperties);
        System.out.println("*********convertAndSend***********");
        this.rabbitTemplate.convertAndSend("exchange", "topic.#", message);
        Object obj = this.rabbitTemplate.convertSendAndReceive("exchange", "topic.#", message);
        System.out.println("*********convertSendAndReceive***********" + JSON.toJSONString(obj.toString()));
    }


    @GetMapping("getUserVO")
    public UserOutVO getUserVO() {
//        System.out.println("同步1");
//        testService.doAsync();
//        System.out.println("同步2");
        return null;
//                UserOutVO.builder().address("地址1111").age(18).name(helloService.sayHello("asfasfd")).birth(new Date()).build();
    }

    @PostMapping("getUserVO")
    @ApiOperation("getUserVO")
    public UserOutVO getUserVO(@RequestBody @Validated UserInVO vo) {
        System.out.println(vo.getSex());
        return UserOutVO.builder().address(vo.getAddress()).age(vo.getAge()).name(vo.getName()).birth(vo.getBirth()).build();
    }
}
