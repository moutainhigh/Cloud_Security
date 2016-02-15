package com.cn.ctbri.service;

import javax.jms.Destination;

import com.cn.ctbri.entity.Task;

public interface IProducerService {
	public void sendMessage(Destination destination, final Task task);
	public void sendMessageTaskId(Destination destination, final String taskId);
	public void sendMessageOrderId(Destination destination, final String orderId);
}
