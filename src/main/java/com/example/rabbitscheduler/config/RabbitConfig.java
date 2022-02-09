package com.example.rabbitscheduler.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {


    @Value("${normalQueue}")
    private String queueName;

    @Value("${normalExchange}")
    private String exchangeName;


    @Value("${normalKey}")
    private String keyName;


    @Bean
    public Queue normalQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Queue waitQueue() {
        return new Queue("wait_queue");
    }

    @Bean
    public DirectExchange normalExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Exchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("wait_custom_exchange", "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding binding(Queue normalQueue, DirectExchange normalExchange) {
        return BindingBuilder.bind(normalQueue).to(normalExchange).with(keyName);
    }

    @Bean
    public Binding waitBinding(@Qualifier(value = "delayExchange") Exchange delayExchange, Queue waitQueue) {
        return BindingBuilder.bind(waitQueue).to(delayExchange).with("wait_key").noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

}
