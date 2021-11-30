package com.example.demo.controller;

import com.example.demo.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/mq")
    public String sendMQ() {
        kafkaProducer.send("xxxxxxxx");
        return "SUCCESS";
    }
}
