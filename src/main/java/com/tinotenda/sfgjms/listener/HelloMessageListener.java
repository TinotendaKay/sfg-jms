package com.tinotenda.sfgjms.listener;

import com.tinotenda.sfgjms.config.JmsConfig;
import com.tinotenda.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       Message message) {

        //  System.out.println("I got a message!! {}"+ helloWorldMessage);


    }

    @JmsListener(destination = JmsConfig.MY_SEND_RECEIVE_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                               @Headers MessageHeaders headers,
                               Message message) throws JMSException {

        HelloWorldMessage myMessage = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), myMessage);

     //   System.out.println("I got a message!! {}" + helloWorldMessage);


    }
}
