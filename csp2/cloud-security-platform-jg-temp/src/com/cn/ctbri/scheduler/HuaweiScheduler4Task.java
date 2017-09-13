//package com.cn.ctbri.scheduler;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.TimeZone;
//
//import javax.mail.Session;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.cn.ctbri.cfg.Configuration;
//import com.cn.ctbri.common.Constants;
//import com.cn.ctbri.common.HuaweiWorker;
//import com.cn.ctbri.entity.Alarm;
//import com.cn.ctbri.entity.AlarmDDOS;
//import com.cn.ctbri.entity.Order;
//import com.cn.ctbri.entity.OrderIP;
//import com.cn.ctbri.entity.TaskHW;
//import com.cn.ctbri.entity.User;
//import com.cn.ctbri.service.IAlarmService;
//import com.cn.ctbri.service.IAssetService;
//import com.cn.ctbri.service.IOrderService;
//import com.cn.ctbri.service.IServService;
//import com.cn.ctbri.service.ITaskHWService;
//import com.cn.ctbri.service.IUserService;
//import com.cn.ctbri.util.Mail;
//import com.cn.ctbri.util.MailUtils;
//import com.cn.ctbri.util.SMSUtils;
//
///**
// * 扫描任务表的华为调度类
// * 
// * @author txr
// * 
// */
//
//@SuppressWarnings("deprecation")
//public class HuaweiScheduler4Task {
//
//    static Logger logger = Logger.getLogger(Scheduler4Task.class.getName());
//
//    private static String taskpage;
//
//    @Autowired
//    ITaskHWService taskhwService;
//    
//    @Autowired
//    IAssetService assetService;
//    
//    @Autowired
//    IServService servService;
//    @Autowired
//    IAlarmService alarmService;
//    @Autowired
//    IUserService userService;
//    
//    @Autowired
//    IOrderService orderService;
//
//    private String destURL = "";
//
//    private String destIP = "";
//
//    private String tplName ="";
//    
//    private int scantime = 0;
//
//    static {
//        try {
//            Properties p = new Properties();
//            p.load(Scheduler4Task.class.getClassLoader().getResourceAsStream("huawei.properties"));
//            taskpage = p.getProperty("TASKPAGE");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void execute() throws Exception {
//        logger.info(new Date()+"[在华为设备创建监控任务]:任务表扫描开始......");
//        /**
//         * 定时要job任务执行的逻辑
//         */
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("status", Integer.parseInt(Constants.TASK_START));   //设置为 已开始？
//        // 获取任务表前n条未下发的记录
//        List<TaskHW> taskhwList = taskhwService.findTaskhw(map);
//        logger.info(new Date()+"[下发任务调度]:当前等待下发的任务有 " + taskhwList.size() + " 个!");
//        // 调用接口下发任务
//        for (TaskHW t : taskhwList) {
//                logger.info(new Date()+"[下发任务调度]:任务-[DDOS_" + t.getTaskId() + "]开始下发!");
//                try {
//                    //根据任务id获取ip
//                    OrderIP ip = taskhwService.getIpByTaskId(t.getOrder_ip_Id());
//                    String ips[] = {ip.getIp()};
//                    //获取token
//                    String token = HuaweiWorker.auth();
//                    //创建监控任务
//                    String value = HuaweiWorker.lssuedCreateZoneTask(token, String.valueOf("DDOS_"+t.getTaskId()), ips);
//                    if(value=="201"){
//                    	logger.info(new Date()+"[在华为设备创建监控任务]:任务-[DDOS_" + t.getTaskId() + "]创建成功!");
//                    	//获取zone_id
//                    	String zone_id = HuaweiWorker.lssuedQueryZoneTask(token, "DDOS_"+t.getTaskId());
//                        t.setZone_id(zone_id);
//                        taskhwService.update(t);
//                    	if(ip.getServiceId()==8){
//                            //创建引流任务
//                            String valueDivert = HuaweiWorker.lssuedCreateDivertTask(token,ips);
//                            if(valueDivert=="201"){
//                                logger.info(new Date()+"[在华为设备创建监控任务]:任务-[DDOS_" + t.getTaskId() + "]创建成功!");
//                            }else if(valueDivert=="409"){
//                                logger.info(new Date()+"[在华为设备创建监控任务]:任务-[DDOS_" + t.getTaskId() + "]创建失败，指定的IP已经存在!");
//                            }else if(valueDivert=="412"){
//                                logger.info(new Date()+"[在华为设备创建监控任务]:任务-[DDOS_" + t.getTaskId() + "]创建失败，token有误!");
//                            }
//                            //更新任务引流状态为1
//                            t.setDrainage(1);
//                            taskhwService.update(t);
//                        }
//                    }else if(value=="409"){
//                    	logger.info(new Date()+"[在华为设备创建监控任务]:任务-[DDOS_" + t.getTaskId() + "]创建失败，名称或IP地址有冲突!");
//                    }else if(value=="412"){
//                    	logger.info(new Date()+"[在华为设备创建监控任务]:任务-[DDOS_" + t.getTaskId() + "]创建失败，token有误!");
//                    }
//                    System.err.println(value);
//                    
//                    
//                } catch (Exception e) {
//                    logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                    continue;
////                    throw new RuntimeException("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                }
//                //为此任务创建调度，定时获取任务结果
//                //更新任务状态为running
//                t.setStatus(Integer.parseInt(Constants.TASK_RUNNING));
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
//                TimeZone.setDefault(tz);
//                Date date = new Date();//获得系统时间.
//                String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
//                t.setExecute_time(sdf.parse(nowTime));
//                taskhwService.update(t);
//                logger.info(new Date()+"[在华为设备创建监控任务]:任务-[DDOS_" + t.getTaskId() + "]完成创建!");
//        }
//        logger.info(new Date()+"[在华为设备创建监控任务]:任务表扫描结束......");
//        
//        
//        logger.info(new Date()+"[在华为设备创建引流任务]:任务表扫描开始......");
//        /**
//         * 定时要job任务执行的逻辑
//         */
//        Map<String, Object> divertmap = new HashMap<String, Object>();
//        divertmap.put("status", Integer.parseInt(Constants.TASK_RUNNING));   //设置为 运行？
//        divertmap.put("drainage", 0);
//        // 获取第一条攻击日志
//        List<TaskHW> tList = taskhwService.findAlarmbyTaskhw(divertmap);
//        logger.info("[在华为设备创建引流任务调度]:当前等待下发的任务有 " + tList.size() + " 个!");
//        // 调用接口下发任务
//        for (TaskHW t : tList) {
//                logger.info("[创建引流任务调度]:任务-[DDOS_" + t.getTaskId() + "]开始下发!");
//                try {
//                    
//                    List<AlarmDDOS> alarmList = alarmService.findAlarmByTaskId(t.getTaskId());//是否有告警信息
//                    if(alarmList!=null && alarmList.size()>0){//如果有告警发邮件和短信通知
//                        List<Order> orderlist=orderService.findOrder(t.getOrder_ip_Id());
//                        Order order=null;
//                        if(orderlist!=null && orderlist.size()>0){
//                            order=orderlist.get(0);
//                            List<User> userlist= userService.findUserById(order.getUserId());
//                            User user=userlist.get(0);
//                            Session session = MailUtils.createSession(Configuration.getServer(),
//                                    Configuration.getName(), Configuration.getPassword());
//                            String from = Configuration.getEmailFrom();
//                            String to = user.getEmail();//邮箱
//                            String phoneNumber = user.getMobile();//联系方式
//                            int sendFlag=order.getMessage();//是否下发短信或邮件
//                            if(sendFlag!=1){
////                                if(!to.equals("") && to!=null){//发邮件
////                                    String subject = "这是来自云安全平台的安全警告";
////                                    String content = "【云安全平台】您好，订单号为：" + order.getId()+"的有安全警告，请及时查看！";
////                                    Mail mail = new Mail(from, to, subject, content);
////                                    try {
////                                        MailUtils.send(session, mail);
////                                        order.setMessage(1);
////                                        orderService.update(order);
////                                    } catch (Exception e) {
////                                        e.printStackTrace();
////                                    }
////                                   }
//                                   
////                                   if(!phoneNumber.equals("") && phoneNumber!=null){
////                                    //发短信给家长
////                                    SMSUtils smsUtils = new SMSUtils();
////                                       String content = "【云安全平台】您好，订单号为：" + order.getId()+"的有安全警告，请及时查看！";
////                                    try {
////                                        smsUtils.sendAlarm(user.getMobile(), content);
////                                        order.setMessage(1);
////                                        orderService.update(order);
////                                    } catch (IOException e1) {
////                                        e1.printStackTrace();
////                                    }
////                                   }
//                            }
//                            //有告警设置为2
//                            order.setStatus(2);
//                            orderService.update(order);
//                        }
//                    }
//                    
//                    
//                    //根据任务id获取ip
//                    OrderIP ip = taskhwService.getIpByTaskId(t.getOrder_ip_Id());
//                    String ips[] = {ip.getIp()};
//                    if(ip.getServiceId()==7){//日常流量监控服务不创建引流
//                        //获取token
//                        String token = HuaweiWorker.auth();
//                        //创建引流任务
//                        String valueDivert = HuaweiWorker.lssuedCreateDivertTask(token,ips);
//                        if(valueDivert=="201"){
//                            logger.info(new Date()+"[在华为设备创建监控任务]:任务-[DDOS_" + t.getTaskId() + "]创建成功!");
//                        }else if(valueDivert=="409"){
//                            logger.info(new Date()+"[在华为设备创建监控任务]:任务-[DDOS_" + t.getTaskId() + "]创建失败，指定的IP已经存在!");
//                        }else if(valueDivert=="412"){
//                            logger.info(new Date()+"[在华为设备创建监控任务]:任务-[DDOS_" + t.getTaskId() + "]创建失败，token有误!");
//                        }
//                        //更新任务引流状态为1
//                        t.setDrainage(1);
//                        taskhwService.update(t);
//                    }
//                } catch (Exception e) {
//                    logger.info("[创建引流任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                    continue;
////                    throw new RuntimeException("[创建引流任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                }
//                
//                logger.info(new Date() +"[在华为设备创建引流任务]:任务-[" + t.getTaskId() + "]完成下发!");
//        }
//        logger.info(new Date()+"[在华为设备创建引流任务]:任务表扫描结束......");
//        
//        
//        logger.info(new Date()+"[在华为设备删除引流任务]:任务表扫描开始......");
//        /**
//         * 定时要job任务执行的逻辑
//         */
//        Map<String, Object> deldivertmap = new HashMap<String, Object>();
//        deldivertmap.put("status", Integer.parseInt(Constants.TASK_RUNNING));   //设置为 运行？
//        deldivertmap.put("drainage", 1);
//        // 获取第一条攻击日志
//        List<TaskHW> dtList = taskhwService.findAlarmbyTaskhw(deldivertmap);
//        logger.info("[删除引流任务调度]:当前等待下发的任务有 " + dtList.size() + " 个!");
//        // 调用接口下发任务
//        for (TaskHW t : dtList) {
//                logger.info("[删除引流任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
//                try {
//                    List<AlarmDDOS> alarmList = alarmService.findEndAlarmByTaskId(t.getTaskId());//是否有告警信息
//                    
//                    if(alarmList.get(0).getEnd_time()!=null){
//                        Order order=null;
//                        List<Order> orderlist=orderService.findOrder(t.getOrder_ip_Id()); 
//                        if(orderlist!=null && orderlist.size()>0){
//                            order=orderlist.get(0);
//                            //有告警设置为1,有结束时间的告警
//                            order.setStatus(1);
//                            orderService.update(order);
//                        }
//                       //根据任务id获取ip
//                        OrderIP ip = taskhwService.getIpByTaskId(t.getOrder_ip_Id());
//                        //获取token
//                        String token = HuaweiWorker.auth();
//                        //删除引流任务
//                        String diverDelete = HuaweiWorker.lssuedDeleteDivertTask(token,ip.getIp());
//                        if(diverDelete=="200"){
//                            logger.info(new Date()+"[删除引流任务调度]:任务-[DDOS_" + t.getTaskId() + "]删除成功!");
//                        }else if(diverDelete=="404"){
//                            logger.info(new Date()+"[删除引流任务调度]:任务-[DDOS_" + t.getTaskId() + "]删除失败，指定的IP不存在!");
//                        }else if(diverDelete=="412"){
//                            logger.info(new Date()+"[删除引流任务调度]:任务-[DDOS_" + t.getTaskId() + "]删除失败，token有误!");
//                        }
//                        //更新任务引流状态为2,已删除引流
//                        t.setDrainage(2);
//                        taskhwService.update(t);
//                    }
//                    
//                } catch (Exception e) {
//                    logger.info(new Date()+"[删除引流任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                    continue;
////                    throw new RuntimeException("[创建引流任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                }
//                
//                logger.info("[下发任务调度]:任务-[" + t.getTaskId() + "]完成下发!");
//        }
//        logger.info(new Date()+"[在华为设备删除引流任务]:任务表扫描结束......");
//        
//        
//        logger.info(new Date()+"[在华为设备删除监控任务]:任务表扫描开始......");
//        /**
//         * 定时要job任务执行的逻辑
//         */
//        Map<String, Object> deletemap = new HashMap<String, Object>();
//        deletemap.put("status", Integer.parseInt(Constants.TASK_RUNNING));   //设置为 已运行？
//        // 获取任务表前n条未删除的记录
//        List<TaskHW> deltaskhwList = taskhwService.findTaskhw(deletemap);
//        logger.info(new Date()+"[删除监控任务调度]:当前等待下发的任务有 " + taskhwList.size() + " 个!");
//        // 调用接口删除任务
//        for (TaskHW t : deltaskhwList) {
//                logger.info(new Date()+"[删除监控任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
//                try {
//                    //获取token
//                    String token = HuaweiWorker.auth();
//                    //删除监控任务
//                    String delete = HuaweiWorker.lssuedDeleteZoneTask(token,String.valueOf(t.getTaskId()));
//                    if(delete=="200"){
//                        logger.info(new Date()+"[删除监控任务调度]:任务-[DDOS_" + t.getTaskId() + "]删除成功!");
//                    }else if(delete=="404"){
//                        logger.info(new Date()+"[删除监控任务调度]:任务-[DDOS_" + t.getTaskId() + "]删除失败，指定的zone不存在!");
//                    }else if(delete=="412"){
//                        logger.info(new Date()+"[删除监控任务调度]:任务-[DDOS_" + t.getTaskId() + "]删除失败，token有误!");
//                    }
//                } catch (Exception e) {
//                    logger.info("[删除监控任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                    continue;
////                    throw new RuntimeException("[删除任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                }
//                //更新任务状态为finish
//                t.setStatus(Integer.parseInt(Constants.TASK_FINISH));
//                taskhwService.update(t);
//                
//                //更新订单告警状态
////                List<Order> oList = orderService.findOrderByTask(task);
////                //获取订单还在执行的任务
////                List<Task> tList = taskService.getTaskStatus(oList.get(0));
////                if(tList.size()==0 && oList.size() > 0){
////                    Order o = oList.get(0);
////                    if(aList.size() > 0){
////                        o.setStatus(Integer.parseInt(Constants.ORDERALARM_YES));
////                    }else{
////                        o.setStatus(Integer.parseInt(Constants.ORDERALARM_NO));
////                    }
////                    orderService.update(o);
////                }
//                logger.info("[删除监控任务调度]:任务-[DDOS_" + t.getTaskId() + "]完成下发!");
//        }
//        logger.info(new Date()+"[在华为设备删除监控任务]:任务表扫描结束......");
//        
//
//    }
//    
//    
//}
