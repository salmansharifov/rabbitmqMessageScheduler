package com.example.rabbitscheduler.controller;

import com.example.rabbitscheduler.entity.Person;
import com.example.rabbitscheduler.rabbit.MsgProducer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/rabbit")
public class RabbitController {

    private final MsgProducer msgProducer;

    @PostMapping("/send")
    public void sendMessage(@RequestBody Person person) {
        msgProducer.produceMessage(person);
    }
}
