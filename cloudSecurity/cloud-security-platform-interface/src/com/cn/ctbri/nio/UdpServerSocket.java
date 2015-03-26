package com.cn.ctbri.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 描     述： UDP服務器端
 * 創建人: 于永波
 * 日    期: 2015-3-16
 */
@Component("serverNetty")
public class UdpServerSocket implements Server{
	/**
	 * 描述:操作日志对象
	 * */
	private static final Logger logger = Logger.getLogger(UdpServerSocket.class.getName());
	@Autowired
	@Qualifier("serverChannelPipelineFactory")
	private UdpServerChannelPipelineFactory pipelineFactory;
	
	private Channel channel;
	
	public void start() {
		//创建UDP通道工厂对象
		DatagramChannelFactory udpChannelFactory = new NioDatagramChannelFactory(
			    Executors.newCachedThreadPool());
		//创建UDP服务器连接对象
		ConnectionlessBootstrap bootstrap = new ConnectionlessBootstrap(udpChannelFactory);
		//绑定参数
		bootstrap.setOption("reuseAddress", false);
	    bootstrap.setOption("child.reuseAddress", false);
	    bootstrap.setOption("readBufferSize", 1024);
	    bootstrap.setOption("writeBufferSize", 1024);
	    //设置管道工厂
	    bootstrap.setPipelineFactory(this.pipelineFactory);
	    SocketAddress serverAddress = new InetSocketAddress(514);
	    this.channel = bootstrap.bind(serverAddress);
	    logger.info("server start on " + serverAddress);
	}

	public void restart() {
		this.stop();
		this.start();
	}

	public void stop() {
		if(this.channel != null) {
			 this.channel.close().addListener(ChannelFutureListener.CLOSE);
		}
	}

}
