package com.ct.listener;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;  
import org.springframework.amqp.rabbit.support.CorrelationData;  
import org.springframework.stereotype.Service;  

  
@Service("confirmCallBackListener")  
public  class ConfirmCallBackListener implements ConfirmCallback{  
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		correlationData = new CorrelationData(UUID.randomUUID().toString());
		 System.out.println("confirm--:correlationData:"+correlationData+",ack:"+ack+",cause:"+cause);
		 if (ack) {  
	            System.out.println("消息成功消费");  
	        } else {  
	            System.out.println("消息消费失败:"+cause);  
	        }  
		
	}

	


}  
