package com.cn.ctbri.scheduler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.cn.ctbri.cfg.Configuration;
import com.cn.ctbri.common.Constants;
import com.cn.ctbri.common.HuaweiWorker;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderIP;
import com.cn.ctbri.entity.TaskHW;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.Mail;
import com.cn.ctbri.util.MailUtils;
import com.cn.ctbri.util.SMSUtils;

/**
 * 扫描任务表的华为调度类
 * 
 * @author txr
 * 
 */

@SuppressWarnings("deprecation")
public class HuaweiScheduler4Task {

    static Logger logger = Logger.getLogger(Scheduler4Task.class.getName());

    private static String taskpage;

    @Autowired
    ITaskHWService taskhwService;
    
    @Autowired
    IAssetService assetService;
    
    @Autowired
    IServService servService;
    @Autowired
    IAlarmService alarmService;
    @Autowired
    IUserService userService;
    
    @Autowired
    IOrderService orderService;

    private String destURL = "";

    private String destIP = "";

    private String tplName ="";
    
    private int scantime = 0;

    static {
        try {
            Properties p = new Properties();
            p.load(Scheduler4Task.class.getClassLoader().getResourceAsStream("arnhem.properties"));
            taskpage = p.getProperty("TASKPAGE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute() throws Exception {
        logger.info("[下发任务调度]:任务表扫描开始......");
        /**
         * 定时要job任务执行的逻辑
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", Integer.parseInt(Constants.TASK_START));   //设置为 已开始？
        // 获取任务表前n条未下发的记录
        List<TaskHW> taskhwList = taskhwService.findTaskhw(map);
        logger.info("[下发任务调度]:当前等待下发的任务有 " + taskhwList.size() + " 个!");
        // 调用接口下发任务
        for (TaskHW t : taskhwList) {
                logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
                try {
                    //根据任务id获取ip
                    OrderIP ip = taskhwService.getIpByTaskId(t.getOrder_ip_Id());
                    String ips[] = {ip.getIp()};
                    //创建监控任务
                    //HuaweiWorker.lssuedCreateZoneTask(String.valueOf(t.getTaskId()), ips);
                    if(ip.getServiceId()==8){
                    	//创建引流任务
                        HuaweiWorker.lssuedCreateDivertTask(ip.getIp());
                    }
                    
               	List<Alarm> alarmList = alarmService.findAlarmByTaskId(t.getTaskId());//是否有告警信息
               	if(alarmList!=null && alarmList.size()>0){//如果有告警发邮件和短信通知
               		List<Order> orderlist=orderService.findOrder(t.getOrder_ip_Id());
                   	Order order=null;
                   	if(orderlist!=null && orderlist.size()>0){
                   		order=orderlist.get(0);
                   		List<User> userlist= userService.findUserById(order.getUserId());
                   		User user=userlist.get(0);
                   		Session session = MailUtils.createSession(Configuration.getServer(),
       			                Configuration.getName(), Configuration.getPassword());
       			        String from = Configuration.getEmailFrom();
                        String to = user.getEmail();//邮箱
                        String phoneNumber = user.getMobile();//联系方式
                   		int sendFlag=order.getMessage();//是否下发短信或邮件
                   		if(sendFlag!=1){
                   			if(!to.equals("") && to!=null){//发邮件
                               	String subject = "这是来自云安全平台的安全警告";
                                String content = "【云安全平台】您好，订单号为：" + order.getId()+"的有安全警告，请及时查看！";
               			        Mail mail = new Mail(from, to, subject, content);
               			        try {
               			            MailUtils.send(session, mail);
               			            order.setMessage(1);
               			            orderService.update(order);
               			        } catch (Exception e) {
               			        	e.printStackTrace();
               			        }
                               }
                               
                               if(!phoneNumber.equals("") && phoneNumber!=null){
                               	//发短信给家长
               		   	        SMSUtils smsUtils = new SMSUtils();
                                   String content = "【云安全平台】您好，订单号为：" + order.getId()+"的有安全警告，请及时查看！";
               		   	        try {
               		   	        	smsUtils.sendAlarm(user.getMobile(), content);
               		   	 		    order.setMessage(1);
           			                orderService.update(order);
               		   	 		} catch (IOException e1) {
               		   	 			e1.printStackTrace();
               		   	 		}
                               }
                   		}
                   	}
               	}
                    
                    
                } catch (Exception e) {
                    logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
                    throw new RuntimeException("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
                }
                //为此任务创建调度，定时获取任务结果
                //更新任务状态为running
                t.setStatus(Integer.parseInt(Constants.TASK_RUNNING));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
                TimeZone.setDefault(tz);
                Date date = new Date();//获得系统时间.
                String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
                t.setExecute_time(sdf.parse(nowTime));
                taskhwService.update(t);
                logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
        }
        logger.info("[下发任务调度]:任务表扫描结束......");
        
        
        
        /**
         * 定时要job任务执行的逻辑
         */
        Map<String, Object> divertmap = new HashMap<String, Object>();
        map.put("status", Integer.parseInt(Constants.TASK_RUNNING));   //设置为 运行？
        // 获取第一条攻击日志
        List<TaskHW> alarmList = taskhwService.findAlarmbyTaskhw(divertmap);
        logger.info("[创建引流任务调度]:当前等待下发的任务有 " + taskhwList.size() + " 个!");
        // 调用接口下发任务
        for (TaskHW t : alarmList) {
                logger.info("[创建引流任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
                try {
                    //根据任务id获取ip
                    OrderIP ip = taskhwService.getIpByTaskId(t.getOrder_ip_Id());
                    if(ip.getServiceId()!=6){//日常流量监控服务不创建引流
                    	//创建引流任务
                        //HuaweiWorker.lssuedCreateDivertTask(ip.getIp());
                    }
                } catch (Exception e) {
                    logger.info("[创建引流任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
                    throw new RuntimeException("[创建引流任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
                }
                //为此任务创建调度，定时获取任务结果
                //更新任务状态为running
                t.setStatus(Integer.parseInt(Constants.TASK_RUNNING));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
                TimeZone.setDefault(tz);
                Date date = new Date();//获得系统时间.
                String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
                t.setExecute_time(sdf.parse(nowTime));
                taskhwService.update(t);
                logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
        }
        logger.info("[下发任务调度]:任务表扫描结束......");
        
        
        logger.info("[删除任务调度]:任务表扫描开始......");
        /**
         * 定时要job任务执行的逻辑
         */
        Map<String, Object> deletemap = new HashMap<String, Object>();
        map.put("status", Integer.parseInt(Constants.TASK_RUNNING));   //设置为 已运行？
        // 获取任务表前n条未删除的记录
        List<TaskHW> deltaskhwList = taskhwService.findTaskhw(deletemap);
        logger.info("[删除任务调度]:当前等待下发的任务有 " + taskhwList.size() + " 个!");
        // 调用接口删除任务
        for (TaskHW t : deltaskhwList) {
                logger.info("[删除任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
                try {
                    //删除监控任务
                    //HuaweiWorker.lssuedDeleteZoneTask(String.valueOf(t.getTaskId()));
                } catch (Exception e) {
                    logger.info("[删除任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
                    throw new RuntimeException("[删除任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
                }
                //更新任务状态为finish
                t.setStatus(Integer.parseInt(Constants.TASK_FINISH));
                taskhwService.update(t);
                logger.info("[删除任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
        }
        logger.info("[删除任务调度]:任务表扫描结束......");
        

    }
    
    
}
