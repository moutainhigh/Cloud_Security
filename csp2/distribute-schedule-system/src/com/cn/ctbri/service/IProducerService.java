package com.cn.ctbri.service;

import java.util.List;

import javax.jms.Destination;

import com.cn.ctbri.entity.Task;

public interface IProducerService {
	public void sendMessage(Destination destination, final Task task);
	public void sendMessageTaskId(Destination destination, final String taskId);
	public void sendMessageOrderId(Destination destination, final String orderId);
	//add by 2016-03-01
	public void sendMessage(Destination resultDestination, List<Task> taskList);
}
