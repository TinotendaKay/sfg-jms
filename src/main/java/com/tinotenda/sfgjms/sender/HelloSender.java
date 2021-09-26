package com.tinotenda.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinotenda.sfgjms.config.JmsConfig;
import com.tinotenda.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        System.out.println("I am sending a message");
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        Message rcvMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RECEIVE_QUEUE, session -> {
            Message helloMessage = null;
            try {
                helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                throw new JMSException("Boom boom bang bang");
            }
            helloMessage.setStringProperty("_type", "com.tinotenda.sfgjms.model.HelloWorldMessage");
            System.out.println("Sending Hello");

            return helloMessage;
        });

        System.out.println("Received Message:: " + rcvMessage.getBody(String.class));

    }
}
