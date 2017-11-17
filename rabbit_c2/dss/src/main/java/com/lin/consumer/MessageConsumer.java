package com.lin.consumer;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.entity.CrtOrder;
//import com.mucfc.QuartzTest;
import com.mucfc.QuartzTest;

/**
 * 功能概要：消费接收
 * 
 * @author linbingwen
 * @since  2016年1月15日 
 */

public class MessageConsumer implements MessageListener {
	
	private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
	@Resource
	private QuartzTest quartzTest;
	@Override
	public void onMessage(Message message) {
		logger.info("receive message:{}",message);
		String s = null;
		try {
			s = new String(message.getBody(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	net.sf.json.JSONObject o = JSONObject.fromObject(s);
		CrtOrder crtO =(CrtOrder) JSONObject.toBean(o,CrtOrder.class);
	/*	ApplicationContext context=new ClassPathXmlApplicationContext("application.xml");
		 quartzTest=context.getBean("quartzTest",QuartzTest.class);
		quartzTest.startSchedule(crtO);*/
		
	}

}
