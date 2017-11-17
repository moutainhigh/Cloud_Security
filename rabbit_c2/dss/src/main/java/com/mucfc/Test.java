package com.mucfc;
import javax.annotation.Resource;

import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cn.ctbri.dao.TaskMapper;
import com.cn.ctbri.model.Task;
import com.entity.CrtOrder;
public class Test {
	
	
	public static void main(String[] args) {
		System.out.println("bbbbbbbbb");
		
		//while(true){
			ApplicationContext context=new ClassPathXmlApplicationContext("application.xml");
			 QuartzTest quartzTest=context.getBean("quartzTest",QuartzTest.class);
			
			 quartzTest.deleteAllJob();
			 CrtOrder crdOrder = new CrtOrder();
			 crdOrder.setAssetId("assetId01");
			 crdOrder.setAssetUrl("assetUrl");
			 crdOrder.setEndDate("2017-11-14 11:52:00");
			 crdOrder.setIsCycle(0);
			 crdOrder.setOrderId("orderId01");
			 crdOrder.setOrigin(1);
			 crdOrder.setStartDate("2017-11-14 11:31:00");
			 quartzTest.startSchedule(crdOrder);
			 //quartzTest.pauseAllJob();
		     //quartzTest.resumeAllJob();
			//
		//}
	}

}
