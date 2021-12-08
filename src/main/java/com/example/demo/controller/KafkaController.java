package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.kafka.Contants;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.kafka.KafkaSender;
import com.example.demo.kafka.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KafkaController {
    private final KafkaProducer kafkaProducer;
    private final  KafkaSender kafkaSender;

    @GetMapping("/mq")
    public String sendMQ() {
        kafkaProducer.send("xxxxxxxx");
        return "SUCCESS";
    }

    @Value("#{'${spring.kafka.listener.topics}'.split(',')}")
    private List<String> topics;

    @GetMapping("/batch/send")
    public String batchSendKafka() {
        User user = User.builder()
                .userName("HS")
                .description("text")
                .createTime(LocalDateTime.now())
                .build();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            user.setId(UUID.randomUUID().toString());
            //日期格式化
            String JSONUser = JSON.toJSONStringWithDateFormat(user, Contants.DateTimeFormat.DATE_TIME_PATTERN,
                    //格式化json
                    SerializerFeature.PrettyFormat);
            kafkaSender.sendMessage(topics.get(0), JSONUser);
        }

        log.info("耗时:{}", (System.currentTimeMillis() - startTime));
        return "SUCCESS";
    }
}
