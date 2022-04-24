//package com.example.demo.rabbitmq;
//
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author wangxing
// * @version 2020/6/25 13:19 Administrator
// */
//@Configuration
//public class RabbitMqConfig {
//    /**
//     * 订单消息实际消费队列所绑定的交换机
//     */
//    @Bean
//    DirectExchange orderDirect() {
//        return (DirectExchange) ExchangeBuilder
//                //设立直接交换机名称
//                .directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
//                //设立持久标记
//                .durable(true)
//                .build();
//    }
//    /**
//     * 订单延迟队列队列所绑定的交换机
//     */
//    @Bean
//    DirectExchange orderTtlDirect() {
//        return (DirectExchange) ExchangeBuilder
//                //设立直接交换机名称
//                .directExchange(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
//                //设立持久标记
//                .durable(true)
//                .build();
//    }
//    /**
//     * 订单实际消费队列
//     */
//    @Bean
//    public Queue orderQueue() {
//        return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName());
//    }
//    /**
//     * 订单延迟队列（死信队列）
//     */
//    @Bean
//    public Queue orderTtlQueue() {
//        return QueueBuilder
//                //创建持久队列构造器
//                .durable(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getName())
//                //设置持久队列参数,设置死信交换机
//                /*
//                在队列上指定一个Exchange，则在该队列上发生如下情况，
//                1.消息被拒绝（basic.reject or basic.nack)，且requeue=false
//                2.消息过期而被删除（TTL）
//                3.消息数量超过队列最大限制而被删除
//                4.消息总大小超过队列最大限制而被删除
//                就会把该消息转发到指定的这个exchange;
//                同时也可以指定一个可选的x-dead-letter-routing-key，表示默认的routing-key，如果没有指定，
//                则使用消息的routeing-key(也跟指定的exchange有关，如果是Fanout类型的exchange，则会转发到所有绑定到该exchange的所有队列
//                */
//                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ORDER_CANCEL.getExchange())//到期后转发的交换机
//                .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())//到期后转发的路由键
//                .build();
//    }
//    /**
//     * 将订单队列绑定到交换机
//     */
//    @Bean
//    Binding orderBinding(DirectExchange orderDirect,Queue orderQueue){
//        return BindingBuilder
//                .bind(orderQueue)
//                .to(orderDirect)
//                .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
//    }
//    /**
//     * 将订单延迟队列绑定到交换机
//     */
//    @Bean
//    Binding orderTtlBinding(DirectExchange orderTtlDirect,Queue orderTtlQueue){
//        return BindingBuilder
//                .bind(orderTtlQueue)
//                .to(orderTtlDirect)
//                .with(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey());
//    }
//
//}