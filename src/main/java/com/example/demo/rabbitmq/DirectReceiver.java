//package com.example.demo.rabbitmq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Slf4j
//@Component
//@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
//public class DirectReceiver {
//
//    @RabbitHandler
//    public void process(String testMessage) {
//        log.info("DirectReceiver消费者收到消息  : {}", new String(testMessage));
//    }
//
//
//
//
//}
