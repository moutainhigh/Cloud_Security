package com.cn.ctbri.nio;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.springframework.stereotype.Component;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
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
	 List<AlarmDDOS> aList = this.getAlarmDDOSByRerult(recMsg);
         
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
 
 /**
  * 根据任务结果解析告警信息
  * 
  * @param taskStr
  * @return
  */
 private List<AlarmDDOS> getAlarmDDOSByRerult(String recMsg) {
     List<AlarmDDOS> aList = new ArrayList<AlarmDDOS>();
     try {
//         String str = "13611111111- songguanghui";
//         String pattern = "(13[4-9]{1}\\d{8}) (\\-{1}) (.+)";
//
//         //以下为验证正则表达式是否匹配
//         Pattern pat = Pattern.compile(pattern);
//         Matcher m = pat.matcher(str);
//         logger.info("server received1:"+m.matches());
         int recMsgNum = recMsg.indexOf(")");
         String one = recMsg.substring(0, recMsgNum+1);
         String two = recMsg.substring(recMsgNum+2);
         String arrays[] = two.split(" ");
         for(int i=0;i<arrays.length;i++){
             logger.info("server received1:"+arrays[i]);
         }
         //前导符
         String prefixChar = recMsg.substring(1,4);
         //时间戳
         String timestamp = recMsg.substring(5,24);
         String ii = recMsg.substring(5,24);
         logger.info("server received1:"+timestamp);
         AlarmDDOS ddos = new AlarmDDOS();
                 
     } catch (Exception e) {
//         logger.info("[获取结果调度]:任务-[" + taskId + "]解析任务结果发生异常!");
//         throw new RuntimeException("[获取结果调度]:任务-[" + taskId + "]解析任务结果发生异常!");
     }
     return aList;
 }
}