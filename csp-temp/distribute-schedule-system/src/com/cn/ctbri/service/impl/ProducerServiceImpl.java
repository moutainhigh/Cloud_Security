package com.cn.ctbri.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Task;
import com.cn.ctbri.service.IProducerService;

@Component  
public class ProducerServiceImpl implements IProducerService{
	private JmsTemplate jmsTemplate;   
	
	//发送task
	public void sendMessage(Destination destination, final Task task) {  
	    System.out.println("---------------生产者发了一个task：taskId=" + task.getTaskId());   
        jmsTemplate.send(destination, new MessageCreator() {   
            public Message createMessage(Session session) throws JMSException {   
                //return session.createTextMessage(task);
            	return session.createObjectMessage(task);
            }   
        });   
		
	}
	
	//发送taskId
	public void sendMessageTaskId(Destination destination, final String taskId) {
	    System.out.println("---------------生产者发了一个taskId：" + taskId);   
        jmsTemplate.send(destination, new MessageCreator() {   
            public Message createMessage(Session session) throws JMSException {   
            	return session.createTextMessage(taskId);
            }   
        }); 
	}

	//发送orderId
	public void sendMessageOrderId(Destination destination, final String orderId) {
		System.out.println("---------------生产者发了一个orderId：" + orderId);   
        jmsTemplate.send(destination, new MessageCreator() {   
            public Message createMessage(Session session) throws JMSException {   
            	return session.createTextMessage(orderId);
            }   
        });
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	@Resource
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	//发送taskList
	public void sendMessage(Destination resultDestination, final List<Task> taskList) {
		for (final Task task : taskList) {
			System.out.println("---------------生产者发了一个task：taskId=" + task.getTaskId());   
	        jmsTemplate.send(resultDestination, new MessageCreator() {   
	            public Message createMessage(Session session) throws JMSException {   
	                //return session.createTextMessage(task);
	            	return session.createObjectMessage(task);
	            }   
	        }); 
		}
		
	}

	

	

}
