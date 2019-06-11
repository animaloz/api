package com.example.openapi.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.MimeType;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

@Configuration
public class RabbitMqConfig {

    public final static String QUEUE_HELLO = "hello";
    public final static String QUEUE_USER = "user";
    public final static String QUEUE_TOPIC_MESSAGE = "topic.message";
    public final static String QUEUE_TOPIC_MESSAGES = "topic.messages";
    public final static String QUEUE_FANOUT_A = "fanout.A";
    public final static String QUEUE_FANOUT_B = "fanout.B";
    public final static String QUEUE_FANOUT_C = "fanout.C";


    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        factory.setMessageConverter(new MessageConverter() {
            @Override
            public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
                System.out.println(messageProperties.getContentType());
                return jackson2JsonMessageConverter.toMessage(object, messageProperties);
            }

            @Override
            public Object fromMessage(Message message) throws MessageConversionException {
                System.out.println(message.getMessageProperties().getContentType());
                return jackson2JsonMessageConverter.fromMessage(message);
            }
        });
        return factory;
    }

    @Bean
    public Queue helloQueue() {
        return new Queue(QUEUE_HELLO);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(QUEUE_USER);
    }

    //===============以下是验证topic Exchange的队列==========
    @Bean
    public Queue queueMessage() {
        return new Queue(QUEUE_TOPIC_MESSAGE);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(QUEUE_TOPIC_MESSAGES);
    }
    //===============以上是验证topic Exchange的队列==========


    //===============以下是验证Fanout Exchange的队列==========
    @Bean
    public Queue aMessage() {
        return new Queue(QUEUE_FANOUT_A);
    }

    @Bean
    public Queue bMessage() {
        return new Queue(QUEUE_FANOUT_B);
    }

    @Bean
    public Queue cMessage() {
        return new Queue(QUEUE_FANOUT_C);
    }
    //===============以上是验证Fanout Exchange的队列==========

    /**
     * exchange有4个类型 direct，topic，fanout，header
     * direct 完全相同
     * topic 可以有通配符 * #
     * fanout 给所有类似于广播
     * header 用的较少
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     * @param queueMessage
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     * @param queueMessages
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

    @Bean
    Binding bindingExchangeA(Queue aMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(aMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue bMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(bMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue cMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(cMessage).to(fanoutExchange);
    }

}
