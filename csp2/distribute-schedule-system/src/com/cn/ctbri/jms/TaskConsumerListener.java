package com.cn.ctbri.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.JMSException; 

import com.cn.ctbri.common.SchedulerTask;
import com.cn.ctbri.entity.Task;


public class TaskConsumerListener implements MessageListener{

	public void onMessage(Message message) {  
        try {  
        	//接收到object消息
        	if(message instanceof ObjectMessage){
        		ObjectMessage om = (ObjectMessage) message;
        	    Task taskRe = (Task) om.getObject();
        	    //引擎调度,任务分发
        	    if(taskRe != null){
            	    SchedulerTask scheduler = new SchedulerTask();
            	    scheduler.schedule(taskRe);
        	    }

        	}  
        } catch (JMSException e) {   
            e.printStackTrace();   
        }   
		
	}

}
