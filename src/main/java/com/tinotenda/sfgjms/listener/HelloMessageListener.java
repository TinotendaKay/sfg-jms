package com.tinotenda.sfgjms.listener;

import com.tinotenda.sfgjms.config.JmsConfig;
import com.tinotenda.sfgjms.model.HelloWorldMessage;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
@Slf4j
public class HelloMessageListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       Message message) {

        System.out.println("I got a message!! {}"+ helloWorldMessage);


    }
}
