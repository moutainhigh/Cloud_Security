package com.cn.ctbri.nio;
import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.springframework.stereotype.Component;
/**
 * 描     述： UDP接收数据监听对象
 * 創建人: 于永波
 * 日    期: 2015-3-16
 */
@Component("receiverHandler")
public class UdpReceiverHandler extends SimpleChannelHandler
{
 
 private static final Logger  logger=Logger.getLogger(UdpReceiverHandler.class.getName());
 
 @Override
 public void messageReceived(ChannelHandlerContext ctx,MessageEvent e) throws Exception
 {
	 ChannelBuffer buffer=(ChannelBuffer)e.getMessage();
	 byte[] recByte=buffer.copy().toByteBuffer().array();
	 String recMsg=new String(recByte);
	 logger.info("server received:"+recMsg.trim());
//	 Random random=new Random();
//	 int backWord=random.nextInt(10000);
//	 ChannelBuffer responseBuffer=new DynamicChannelBuffer(8);
//	 responseBuffer.readBytes(backWord);
//	 e.getChannel().write(responseBuffer);
	 ChannelBuffer responseBuffer= new DynamicChannelBuffer(1);
     responseBuffer.writeBytes("A".getBytes());
     //write to the client
     e.getChannel().write(responseBuffer, e.getRemoteAddress());
 }
 
 @Override
 public void exceptionCaught(ChannelHandlerContext ctx,ExceptionEvent e)
 {
	  logger.error(e.getCause());
	  if(e.getChannel() !=null)
	  {
	   //e.getChannel().close().addListener(ChannelFutureListener.CLOSE);
	  }
 }
}