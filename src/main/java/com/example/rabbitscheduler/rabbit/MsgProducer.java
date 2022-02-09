package com.example.rabbitscheduler.rabbit;

import com.example.rabbitscheduler.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MsgProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Binding binding;

    @Value("${normalExchange}")
    public String exchangeName;


    @Value("${normalKey}")
    public String keyName;


    public void produceMessage(Person person) {
        log.info("message for rabbit: {}", person.toString());
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), person);
        rabbitTemplate.convertAndSend("wait_custom_exchange", "wait_key", person, message -> {
            message.getMessageProperties().setDelay(180000);
            return message;
        });
        log.info("message is sent successfully");
    }
}
