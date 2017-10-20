package com.mucfc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Test {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
	 QuartzTest quartzTest=context.getBean("quartzTest",QuartzTest.class);
	       // quartzTest.startSchedule();
            quartzTest.resumeJob();

	}

}
