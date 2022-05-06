package com.example.emo.kafka.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.emo.kafka.service.KafkaProducer;
import com.example.emo.kafka.service.KafkaSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KafkaController {
    private final KafkaProducer kafkaProducer;
    private final KafkaSender kafkaSender;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/mq")
    public String sendMQ(Integer amount) {
        int count = 0;
        JSONObject jsonObject = JSONObject.parseObject("{\"data\":[{\"creator\":\"wdqy\",\"deviceTypeId\":\"62184799e151b539b6fa2303\",\"originOrgId\":\"64567ef1a8cd41b49f9fffa573dbad32\",\"isConnect\":2,\"origin\":\"telit\",\"deviceKey\":\"testcallback014\",\"updateTime\":\"2022-03-30 06:58:42\",\"deviceName\":\"testcallback014\",\"orgId\":\"fa51fec5bd8e4554a1084a41afc84c86\",\"deviceTypeKey\":\"f8d3d21fbec7421e81f56af1e7f02860\",\"createTime\":\"2022-03-30 06:58:42\",\"displayDeviceKey\":\"testcallback014\",\"parentDeviceId\":\"\",\"location\":\"\",\"id\":\"6243ffa23821d1529dcf7dda\",\"status\":0}],\"deviceKey\":\"62184799e151b539b6fa2303\",\"type\":\"1\"}");
        kafkaProducer.send(jsonObject.toString());

        return "SUCCESS";
    }

    @Value("#{'${spring.kafka.listener.topics}'.split(',')}")
    private List<String> topics;

    @GetMapping("/batch/send")
    public String batchSendKafka() {

        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send("test-0001", "xxxxx");
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("failure");
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info("success");
            }
        });

        return "SUCCESS";
    }

}
