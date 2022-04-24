package com.example.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * 类功能描述：<br>
 * <ul>
 * <li>类功能描述1<br>
 * <li>类功能描述2<br>
 * <li>类功能描述3<br>
 * </ul>
 * 修改记录：<br>
 * <ul>
 * <li>修改记录描述1<br>
 * <li>修改记录描述2<br>
 * <li>修改记录描述3<br>
 * </ul>
 *
 * @author xuefl
 * @version 5.0 since 2020-01-13
 */
@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = "test-0001", groupId = KafkaProducer.TOPIC_GROUP1)
    public void topic_test_grou001(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test_group001,Message is {}", msg);
            ack.acknowledge();
        }
    }


    @KafkaListener(topics = "test-0001", groupId = KafkaProducer.TOPIC_GROUP2)
    public void topic_test_group002(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        Optional<?> value = Optional.ofNullable(record.value());
        if (value.isPresent()) {
            log.info("topic_test_group002,Message is {}", value.get());
            ack.acknowledge();
        }

    }

//    @KafkaListener(topics = KafkaProducer.TOPIC_TEST, groupId = KafkaProducer.TOPIC_GROUP2)
//    public void topic_test1(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
//
//        Optional message = Optional.ofNullable(record.value());
//        long offset = record.offset();
//        if (message.isPresent()) {
//            Object msg = message.get();
//            log.info("topic_test1 消费了： Topic:" + topic + ",Message:" + msg);
//            ack.acknowledge();
//        }
//    }

//    @KafkaListener(topics = KafkaProducer.TOPIC_TEST, groupId = KafkaProducer.TOPIC_GROUP1)
//    public void topic_batch_test(List<ConsumerRecord<?, ?>> integerStringConsumerRecords, Acknowledgment acknowledgment) {
//        Iterator<ConsumerRecord<?, ?>> it = integerStringConsumerRecords.iterator();
//        while (it.hasNext()) {
//            ConsumerRecord<?, ?> consumerRecords = it.next();
//            Optional message = Optional.ofNullable(consumerRecords.value());
//            log.info("topic_batch_test 消费了：Message:" + message);
//            acknowledgment.acknowledge();
//        }
//    }


}