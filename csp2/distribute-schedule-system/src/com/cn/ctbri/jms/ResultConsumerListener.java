package com.cn.ctbri.jms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cn.ctbri.common.SchedulerResult;
import com.cn.ctbri.common.SchedulerTask;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IProducerService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.Respones;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ResultConsumerListener  implements MessageListener{
	//日志对象
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
    private ITaskService taskService; 
	@Autowired
    ITaskWarnService taskWarnService;
	@Autowired
    IAlarmService alarmService;	
    
	public void onMessage(Message message) {
		JSONObject json = new JSONObject();
	    ClientConfig config = new DefaultClientConfig();
        //检查安全传输协议设置
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource("http://localhost:8080/dss/rest/internalapi//vulnscan/re_orderTaskid");

        try {  
        	//接收到object消息
        	if(message instanceof TextMessage){
        		TextMessage tm = (TextMessage) message;
        		String taskid = tm.toString();
        	    if(taskid!=null && !taskid.equals("")){
    				//taskId
    				int taskId = Integer.parseInt(taskid);
    				SchedulerResult rst = new SchedulerResult();

					rst.getscanResult(taskId);
					//根据taskId查询task
					Task task = taskService.findTaskById(taskId);
					//如果是创宇需查数据库获取结果
					if(task.getWebsoc()==1){
						if(task.getService_id()==5){//可用性检测
							//根据groupId查询alarm表及taskwarn表
							List<TaskWarn> taskwarnList = taskWarnService.findTaskWarnByGroupId(task.getGroup_id());
							if(taskwarnList!=null && taskwarnList.size()>0){
								//返回结果

								net.sf.json.JSONObject taskwarnObject = new net.sf.json.JSONObject().fromObject(taskwarnList.get(0));
								net.sf.json.JSONObject taskObject = new net.sf.json.JSONObject().fromObject(task);
								json.put("taskwarnObj", taskwarnObject);
								json.put("taskObj", taskObject);
								json.put("alarmObj", "");
								json.put("result", "success");
							}
						}else{//其他
							Map<String,Object> paramMap = new HashMap<String,Object>();
							paramMap.put("group_id", task.getGroup_id());
							List<Alarm> alarmList = alarmService.findAlarmBygroupId(paramMap);
							if(alarmList!=null && alarmList.size()>0){
								//返回结果
								net.sf.json.JSONObject alarmObject = new net.sf.json.JSONObject().fromObject(alarmList.get(0));
								net.sf.json.JSONObject taskObject = new net.sf.json.JSONObject().fromObject(task);
								json.put("alarmObj", alarmObject);
								json.put("taskObj", taskObject);
								json.put("taskwarnObj", "");
								json.put("result", "success");
							}
						}
					} 
        	    }
        	}  
        } catch (Exception e) {   
            e.printStackTrace();
            
	        try {
				json.put("result", "fail");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

        } 
        //推送结果
        ClientResponse response = service.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, json);        
        System.out.println(response.toString());

        String addresses = response.getEntity(String.class);
        System.out.println(addresses); 
		
	}

}
