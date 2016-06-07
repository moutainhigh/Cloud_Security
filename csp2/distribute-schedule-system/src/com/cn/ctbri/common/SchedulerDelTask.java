package com.cn.ctbri.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.Destination;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cn.ctbri.entity.EngineCfg;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.logger.CSPLoggerAdapter;
import com.cn.ctbri.logger.CSPLoggerConstant;
import com.cn.ctbri.service.IEngineService;
import com.cn.ctbri.service.IOrderTaskService;
import com.cn.ctbri.service.IProducerService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.util.DateUtils;

/**
 * 删除订单任务表的调度类
 * 
 * @author tangxr 
 * 
 */

@SuppressWarnings("deprecation")
public class SchedulerDelTask {

	static Logger logger = Logger.getLogger(SchedulerDelTask.class.getName());

	@Autowired
    ITaskService taskService;
	@Autowired
    IEngineService engineService;

	public void execute() throws Exception {
		/**
         * 定时要job任务删除的逻辑
         */
        Map<String, Object> delmap = new HashMap<String, Object>();
        delmap.put("status", Integer.parseInt(Constants.TASK_RUNNING));
        delmap.put("serviceId", 5);//可用性Id
        // 获取任务表前n条未完成的记录
        List<Task> taskDelList = taskService.findDelTask(delmap);
        // 调用接口删除任务
        for (Task t : taskDelList) {
			EngineCfg en = engineService.getEngineById(t.getEngine());
			
            logger.info("[删除任务调度]:任务-[" + t.getTaskId() + "]获取状态!");
            String sessionId = SouthAPIWorker.getSessionId(en.getEngine_number());
            String resultStr = SouthAPIWorker.getStatusByTaskId(en.getEngine_number(), String.valueOf(t.getTaskId())+"_"+t.getOrder_id());
	        String status = this.getStatusByResult(resultStr);
	        
            if("running".equals(status)){
                logger.info("[删除任务调度]:任务-[" + t.getTaskId() + "]开始下发!");
                try {
                	//删除安恒可用性任务
                	SouthAPIWorker.removeTask(en.getEngine_number(), String.valueOf(t.getTaskId())+"_"+t.getOrder_id());
                        
                    //任务完成后,引擎活跃数减1
                    en.setId(t.getEngine());
                    engineService.updatedown(en);
                    //更新任务状态为finish
                    t.setStatus(Integer.parseInt(Constants.TASK_FINISH));
                    taskService.update(t);
                } catch (Exception e) {
                    logger.info("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
//                    throw new RuntimeException("[下发任务调度]: 下发任务失败，远程存在同名任务请先删除或重新下订单!");
                    continue;
                }
                logger.info("[删除任务调度]:任务-[" + t.getTaskId() + "]完成删除!");
            }else{
                logger.info("[删除任务调度]: 任务-[" + t.getTaskId() + "]下发失败!，远程存在同名任务请先删除或重新下订单!");
            }

        }
        logger.info("[删除任务调度]:任务表扫描结束......");
	}
	
	
	private String getStatusByResult(String resultStr) {
		String state = "";
		try {
			String status = JSONObject.fromObject(resultStr).getString("status");
			if("Success".equals(status)){
				state = JSONObject.fromObject(resultStr).getString("State");
			}else if("Fail".equals(status)){
				String errorMsg = JSONObject.fromObject(resultStr).getString("ErrorMsg");
				if(errorMsg.contains("not found")){
					state = "";
				}
				CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]:远程没有此任务，可以下发!;User="+null);
			}
			return state;
		} catch (Exception e) {
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER, "Date="+DateUtils.nowDate()+";Message=[下发任务调度]:解析任务状态失败,远程没有此任务，可以下发!;User="+null);
			return "";
		}
		
	}
	
}
