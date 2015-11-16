package com.sinosoft.scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartQuartz {
	public static void main(String[] args) throws IOException {
		new ClassPathXmlApplicationContext("db-db-job.xml");  
	}
}
