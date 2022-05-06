/*
package com.example.demo.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

*/
/**
 * @Auther: hs
 * @Date: 2019/3/7 00:53
 * @Description: 
 *//*

@Component
public class UserTask {

    @Value("#{'${kafka.listener.topics}'.split(',')}")
    private List<String> topics;

    private final MultiService MUlTI_SERVICE;

    private final KafkaSender KAFKA_SENDER;

    @Autowired
    public UserTask(MultiService multiService, KafkaSender kafkaSender){
        this.MUlTI_SERVICE = multiService;
        this.KAFKA_SENDER = kafkaSender;
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void addUserTask() {
        User user=new User();
        user.setUserName("HS");
        user.setDescription("text");
        user.setCreateTime(LocalDateTime.now());
        String JSONUser = JSON.toJSONStringWithDateFormat(user,
                Contants.DateTimeFormat.DATE_TIME_PATTERN,//日期格式化
                SerializerFeature.PrettyFormat);//格式化json
        for (int i = 0; i < 700; i++) {
            KAFKA_SENDER.sendMessage(topics.get(0), JSONUser);
        }
        MUlTI_SERVICE.addUser(user);
    }
}


*/
