package com.ct.service;

import org.springframework.amqp.core.AmqpTemplate;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
  
@Service("publishService")  
public class PublishService {  
    @Autowired    
    private AmqpTemplate amqpTemplate;   
      //发送消息
    public void send(String exchange, String routingKey, Object message) {    
        amqpTemplate.convertAndSend(exchange, routingKey, message);  
    }    
}  
