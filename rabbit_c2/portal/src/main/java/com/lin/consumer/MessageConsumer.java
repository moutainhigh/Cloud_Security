package com.lin.consumer;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.entity.CrtOrder;

/**
 * 功能概要：消费接收
 * 
 * @author linbingwen
 * @since  2016年1月15日 
 */
public class MessageConsumer implements MessageListener {
	
	private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

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
	}

}
