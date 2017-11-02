package com.cn.ctbri.nio;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 描     述： UDP通道工厂 
 * 創建人: 于永波
 * 日    期: 2015-3-16
 */
@Component("serverChannelPipelineFactory")
public class UdpServerChannelPipelineFactory implements ChannelPipelineFactory {
	@Autowired
	@Qualifier("receiverHandler")
	private UdpReceiverHandler handler;
	
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("handler", this.handler);
		return pipeline;
	}

	public void setHandler(UdpReceiverHandler handler) {
		this.handler = handler;
	}
}