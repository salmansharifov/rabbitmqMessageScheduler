package com.example.rabbitscheduler.rabbit;

import com.example.rabbitscheduler.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MsgConsumer {


    @RabbitListener(queues = "${normalQueue}")
    public void consumeMessage(Person person) {
        log.info("message is consumed: {}", person.toString());
    }

    @RabbitListener(queues = "wait_queue")
    public void consumeWaitMessage(Person person) {
        log.info("message is consumed from wait Queue: {}", person.toString());
    }
}
