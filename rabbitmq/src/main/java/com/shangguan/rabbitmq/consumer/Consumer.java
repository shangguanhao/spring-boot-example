package com.shangguan.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("rabbitmq_queue"))
    public void process(String message) {
        System.out.println("消费者消费消息111=====" + message);
    }

//    @RabbitHandler
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue("myQueue"),
//            key = "mobi",
//            exchange = @Exchange("myExchange")
//    ))
//    public void process1(String message) {
//        System.out.println("消费者消费消息222=====" + message);
//    }
}