package com.cn.ctbri.nio;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.common.Constants;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.AlarmDDOS;
import com.cn.ctbri.service.IAlarmDDOSService;
import com.cn.ctbri.service.ITaskService;
/**
 * 描     述： UDP接收数据监听对象
 * 創建人: 于永波
 * 日    期: 2015-3-16
 */
@Component("receiverHandler")
public class UdpReceiverHandler extends SimpleChannelHandler
{
 
 private static final Logger  logger=Logger.getLogger(UdpReceiverHandler.class.getName());
 @Autowired
 private IAlarmDDOSService alarmDDOSService;
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
     logger.info("收到消息:"+recMsg);
     
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
         //日志类型
         String log_type = two.substring(two.indexOf("log_type") + "log_type".length(), two.length() - two.indexOf("log_type") - "log_type".length());
         log_type = log_type.substring(1, log_type.indexOf(" "));
         //攻击类型
         String attack_type = two.substring(two.indexOf("attack_type") + "attack_type".length(), two.length() - two.indexOf("attack_type") - "attack_type".length());
         attack_type = attack_type.substring(1, attack_type.indexOf(" "));
         //攻击开始时间
         String start_time_attack = two.substring(two.indexOf("start_time_attack") + "start_time_attack".length(), two.length() - two.indexOf("start_time_attack") - "start_time_attack".length());
         start_time_attack = start_time_attack.substring(2, 21);
         //攻击源IP
         String attacker = two.substring(two.indexOf("attacker") + "attacker".length(), two.length() - two.indexOf("attacker") - "attacker".length());
         attacker = attacker.substring(1, attacker.indexOf(" "));
         //攻击持续时间(秒)（处理已结束）
         String duration = two.substring(two.indexOf("duration") + "duration".length(), two.length() - two.indexOf("duration") - "duration".length());
         duration = duration.substring(1, duration.indexOf(" "));
         //攻击结束时间（处理已结束）
         String end_time = two.substring(two.indexOf("end_time") + "end_time".length(), two.length() - two.indexOf("end_time") - "end_time".length());
         end_time = end_time.substring(2, 21);
         boolean isEndTime = isTimeLegal(end_time);
         //告警上报时间
         String start_time_alert = two.substring(two.indexOf("start_time_alert") + "start_time_alert".length(), two.length() - two.indexOf("start_time_alert") - "start_time_alert".length());
         start_time_alert = start_time_alert.substring(2, 21);
         boolean isAlertTime = isTimeLegal(start_time_alert);
         //zone_id
         String zone_id = two.substring(two.indexOf("zone_id") + "zone_id".length(), two.length() - two.indexOf("zone_id") - "zone_id".length());
         zone_id = zone_id.substring(1, zone_id.indexOf(" "));
         
         
//         //前导符
//         String prefixChar = recMsg.substring(1,4);
//         //时间戳
//         String timestamp = recMsg.substring(5,24);
//         String ii = recMsg.substring(5,24);
//         logger.info("server received1:"+timestamp);
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         AlarmDDOS ddos = new AlarmDDOS();
         ddos.setAttack_type(attack_type);
         ddos.setStart_time_attack(sdf.parse(start_time_attack));
         ddos.setAttacker(attacker);
         ddos.setDuration(duration);
         ddos.setAttack_flow(null);
         if(isEndTime==true){
             ddos.setEnd_time(sdf.parse(end_time));
         }
         if(isAlertTime==true){
             ddos.setStart_time_alert(sdf.parse(start_time_alert));
         }
         ddos.setTaskId(Integer.parseInt(zone_id));
         alarmDDOSService.saveAlarmDDOS(ddos);
         logger.info(new Date()+"[接收]来自[华为设备]消息[成功][接收" + zone_id + "攻击日志]!");
//         alarmDDOSService.updeteAlarmDDOS(ddos);
                 
     } catch (Exception e) {
         e.printStackTrace();
         logger.info(new Date()+"[接收]来自[华为设备]消息[失败]!");
         throw new RuntimeException(new Date()+"[接收]来自[华为设备]消息[失败]!");
     }
     return aList;
 }
 
 /** 
  * 判断输入的字符串是否满足时间格式 ： yyyy-MM-dd HH:mm:ss 
  * @param patternString 需要验证的字符串 
  * @return 合法返回 true ; 不合法返回false 
  */  
 public static boolean isTimeLegal(String patternString) {  
          
      Pattern a=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");   
      Matcher b=a.matcher(patternString);   
      if(b.matches()) {  
            return true;  
      } else {  
            return false;  
      }  
 }  
}