package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.kafka.Contants;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.kafka.KafkaSender;
import com.example.demo.kafka.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class KafkaController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/mq")
    public String sendMQ() {
        kafkaProducer.send("xxxxxxxx");
        return "SUCCESS";
    }


    @Autowired
    private KafkaSender kafkaSender;

    @Value("#{'${spring.kafka.listener.topics}'.split(',')}")
    private List<String> topics;

    @GetMapping("/batch/send")
    public String batchSendKafka() {
        User user = new User();
        user.setUserName("HS");
        user.setDescription("text");
        user.setCreateTime(LocalDateTime.now());

        for (int i = 0; i < 700; i++) {
            user.setId(UUID.randomUUID().toString());
            //日期格式化
            String JSONUser = JSON.toJSONStringWithDateFormat(user, Contants.DateTimeFormat.DATE_TIME_PATTERN,
                    //格式化json
                    SerializerFeature.PrettyFormat);
            kafkaSender.sendMessage(topics.get(0), JSONUser);
        }
        return "SUCCESS";
    }
}
