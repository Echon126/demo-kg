//package com.example.demo.kafka;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Component
//public class KafkaListeners {
//
//    private static int count = 0;
//    private static int timeCount = 0;
//
//    @KafkaListener(containerFactory = "kafkaBatchListener6", topics = {"#{'${spring.kafka.listener.topics}'.split(',')[0]}"}, groupId = KafkaProducer.TOPIC_GROUP1)
//    public void batchListener(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
//        long startTime = System.currentTimeMillis();
//        List<User> userList = new ArrayList<>();
//        try {
//            records.forEach(record -> {
//                User user = JSON.parseObject(record.value().toString(), User.class);
//                user.getCreateTime().format(DateTimeFormatter.ofPattern(Contants.DateTimeFormat.DATE_TIME_PATTERN));
//                userList.add(user);
//            });
//        } catch (Exception e) {
//            log.error("Kafka监听异常" + e.getMessage(), e);
//        } finally {
//            ack.acknowledge();//手动提交偏移量
//        }
//        String pretty = JSON.toJSONString(userList, SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullListAsEmpty);
//        count += userList.size();
//        timeCount += (System.currentTimeMillis() - startTime);
//        log.info("批量消费数据大小:{} ,count is :{},total cost time is:{}", userList.size(), count, timeCount);
//
//    }
//
//}
//
