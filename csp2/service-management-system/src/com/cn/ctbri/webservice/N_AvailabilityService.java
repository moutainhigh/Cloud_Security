package com.cn.ctbri.webservice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;

import com.cn.ctbri.common.InternalWorker;
import com.cn.ctbri.common.ManagerWorker;
import com.cn.ctbri.constant.WarnType;
import com.cn.ctbri.entity.API;
import com.cn.ctbri.entity.APINum;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAPI;
import com.cn.ctbri.entity.OrderTask;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IOrderAPIService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IOrderTaskService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.Common;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.Random;
import com.cn.ctbri.util.Respones;
/**
 * ??? ??? ???  ???  tangxr
 * ???????????????  2015-10-19
 * ???        ??????  ????????????WebService????????????
 * ???        ??????  1.0
 */
@Component
@Path("openapi/availability")
public class N_AvailabilityService {
	@Autowired
    private ITaskService taskService;
	@Autowired
    private IOrderService orderService;
	@Autowired
    private IAlarmService alarmService;
	@Autowired
    private IOrderTaskService orderTaskService;
	@Autowired
    private IOrderAPIService orderAPIService;
	@Autowired
    private IUserService userService;
	@Autowired
    ITaskWarnService taskWarnService;
	
	//????????????????????????????????????
	@POST
    @Path("/order/{token}")
	@Produces(MediaType.APPLICATION_JSON) 
    public String vulnScanCreate(@PathParam("token") String token, String dataJson) {
		JSONObject json = new JSONObject();
		try {
			//??????token??????user
			User user = userService.findUserByToken(token);
			if(token!=null && token!="" && user!=null){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("apiKey", user.getApikey());
		        paramMap.put("apiId", 5);
		        //?????????????????????API??????
				List<OrderAPI> oAPIList = orderAPIService.findByParam(paramMap);
				//????????????????????????????????????????????????api??????
				if(user.getType()==3 || (user.getType()==2 && oAPIList.size()>0)){
					//??????????????????
					Date end = null;
					if(user.getType()!=3){
						end = oAPIList.get(0).getEnd_date();
					}
					//????????????
					Date now = new Date();
					if(user.getType()==3 || now.compareTo(end)<=0){
						//??????????????????api????????????????????????
						List<OrderAPI> userableList = orderAPIService.findUseableByParam(paramMap);
						if(user.getType()==3 || userableList.size()>0){
							JSONObject jsonObj = new JSONObject().fromObject(dataJson);
							//????????????
							String startTime = jsonObj.getString("startTime");
							//????????????
							String endTime = jsonObj.getString("endTime");
						    //??????
							String scanPeriod = jsonObj.getString("scanPeriod");
							//????????????
							String targetURL = jsonObj.getString("targetURL");
							

						    Date date = DateUtils.stringToDateNYRSFM(startTime);
							
							boolean flag = Common.isSameDate(date);
							if(!flag){
								json.put("code", 404);
								json.put("message", "startTime???????????????????????????????????????????????????!");
								return json.toString();
							}
							boolean urlStatus = Common.urlCheck(targetURL);
							if(urlStatus){
								json.put("code", 404);
								json.put("message", "targetURL??????????????????????????????????????????????????????!");
								return json.toString();
//								return "targetURL??????????????????????????????????????????????????????!";
							}
							
							//????????????id??????????????????5????????????
							SimpleDateFormat odf = new SimpleDateFormat("yyMMddHHmmss");//??????????????????
							String orderDate = odf.format(new Date());
					        String orderId = orderDate+String.valueOf(Random.fivecode());
							//????????????
					        Order order = new Order();
					        order.setId(orderId);
							order.setServiceId(5);//?????????
					        order.setType(1);//?????????
					        
					        int scan_type = 0;
					        if(scanPeriod.equals("M1")){//10??????
					        	scan_type = 1;
					        }else if(scanPeriod.equals("M3")){//30??????
					        	scan_type = 2;
					        }else if(scanPeriod.equals("H")){//1??????
					        	scan_type = 3;
					        }else{
					        	json.put("code", 426);
								json.put("message", "?????????????????????????????????????????????");
								return json.toString();
					        }
					        Date begin_date = DateUtils.stringToDateNYRSFM(startTime);
					        Date end_date = DateUtils.stringToDateNYRSFM(endTime);
					        //????????????api??????
					        long times = getTask(begin_date, end_date, scan_type);
					        if(user.getType()!=3 && times > userableList.get(0).getNum()){
					        	json.put("code", 426);
					        	json.put("message", "???????????????????????????????????????????????????????????????");
								return json.toString();
					        }
					        
					        order.setBegin_date(begin_date);
					        order.setEnd_date(end_date);
					        order.setScan_type(scan_type);
					        order.setCreate_date(now);
					        order.setTask_date(begin_date);
					        order.setStatus(0);//????????????????????????0?????????
					        //??????????????????Id add by tangxr 2016-4-9
					        order.setUserId(user.getId());
					        //end
					        orderService.insertOrder(order);
					        
					        //????????????????????????
					        OrderTask orderTask = new OrderTask();
					        orderTask.setOrderId(orderId);
					        orderTask.setServiceId(5);//?????????
					        orderTask.setType(1);//?????????
					        orderTask.setBegin_date(begin_date);
					        orderTask.setEnd_date(end_date);
							orderTask.setScan_type(scan_type);
							orderTask.setStatus(0);
							orderTask.setUrl(targetURL);
							orderTask.setTask_status(1);//??????????????????????????????1?????????
							orderTask.setTask_date(begin_date);
							orderTaskService.insertOrderTask(orderTask);
							
					        if(user.getType()!=3){
					        	//??????api?????? add by tangxr 2016-4-9
						        OrderAPI orderAPI = userableList.get(0);
						        
						        Map<String, Object> param = new HashMap<String, Object>();
								param.put("id", orderAPI.getId());
						        param.put("count", times);
						        orderAPIService.updateCount(param);
						        
						        //insert????????????
								APINum num = new APINum();
								num.setApikey(user.getApikey());
								num.setService_type(5);
								num.setApi_type(1);
								num.setStatus(1);
								num.setCreate_time(new Date());
								num.setCount(times);
								
								num.setApiId(orderAPI.getId());
								num.setToken(token);
								num.setOrderId(orderId);
								userService.insertAPINum(num);
								ManagerWorker.createAPINum(user.getApikey(), 5, 1, 1);
					        }
					        
					        json.put("code", 201);//??????201????????????
							json.put("orderId", orderId);
						}else{
							json.put("code", 425);
							json.put("message", "????????????????????????????????????????????????");
						}
					}else{
						json.put("code", 424);
						json.put("message", "?????????????????????????????????");
					}
				}else{
					json.put("code", 423);
					json.put("message", "???????????????");
				}
				
			}else{
				json.put("code", 422);
				json.put("message", "token??????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//??????404????????????
			json.put("message", "??????????????????");
		} 
		return json.toString();
	}
		
	//????????????
	@PUT
    @Path("/order/{orderId}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_opt_orderTask(@PathParam("token") String token, @PathParam("orderId") String orderId, String dataJson) {
		JSONObject json = new JSONObject();
		try {
			//??????token??????user
			User user = userService.findUserByToken(token);
			if(token!=null && token!="" && user!=null){
				Order order = orderService.findOrderByOrderId(orderId);
				//??????token?????? add by tangtang 2016-9-21
				boolean apikey = false;
				boolean userId = false;
				if(order.getApiKey()!=null){
					apikey = order.getApiKey().equals(user.getApikey());
				}
				if(String.valueOf(order.getUserId())!=null){
					userId = String.valueOf(order.getUserId()).equals(String.valueOf(user.getId()));
				}
				if(order!=null && order.getServiceId()==5 && (apikey||userId)){
					JSONObject jsonObj = new JSONObject().fromObject(dataJson);
					String opt = jsonObj.getString("opt");
					Order o = orderService.findOrderByOrderId(orderId);
		//			OrderTask ot = orderTaskService.findByOrderId(orderId);
					String result = InternalWorker.vulnScanOptOrderTask(orderId,opt);
			        if(result.equals("success")){
		//	        	if(o.getStatus()==4){
			        	if("stop".equals(opt)){
			        		o.setStatus(5);//stop
		//	        	}else if(o.getStatus()==5){
			        	}else if("resume".equals(opt)){
			        		o.setStatus(4);//start
			        	}
			        	orderService.update(o);
			        	
			        	this.countAPI(token, orderId, null, 5, 2);
			        	
			        	json.put("code", "200");
			        	json.put("message", "??????????????????");
			        }else if(result.equals("wrong")){
			        	json.put("code", "431");
			        	json.put("message", "???????????????????????????????????????");
			        }else if(result.equals("wait")){
			        	json.put("code", "424");
			        	json.put("message", "???????????????");
			        }else if(result.equals("other")){
			        	json.put("code", "423");
			        	json.put("message", "???????????????????????????????????????????????????");
			        }else{
			        	if(o.getStatus()==2||o.getStatus()==1){
			        		json.put("code", "423");
				        	json.put("message", "???????????????????????????????????????????????????");
			        	}else{
			        		json.put("code", "404");
				        	json.put("message", "??????????????????");
			        	}
			        }
				}else{
					json.put("code", 421);
					json.put("message", "???????????????");
				}
			}else{
				json.put("code", 422);
				json.put("message", "token??????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "404");
			json.put("message", "??????????????????");
		}
		return json.toString();
    }
	
	//??????status
	@GET
    @Path("/orderStatus/{orderId}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_Get_orderStatus(@PathParam("token") String token,@PathParam("orderId") String orderId, @PathParam("taskId") String taskId) {
		JSONObject json = new JSONObject();
		try {
			//??????token??????user
			User user = userService.findUserByToken(token);
			if(token!=null && token!="" && user!=null){
				
				Order order = orderService.findOrderByOrderId(orderId);
				//??????token?????? add by tangtang 2016-9-21
				boolean apikey = false;
				boolean userId = false;
				if(order.getApiKey()!=null){
					apikey = order.getApiKey().equals(user.getApikey());
				}
				if(String.valueOf(order.getUserId())!=null){
					userId = String.valueOf(order.getUserId()).equals(String.valueOf(user.getId()));
				}
				if(order!=null && order.getServiceId()==5 && (apikey||userId)){
					//taskId ?????????????????????????????????????????????
					if(taskId!=null && taskId!=""){
						Task task = new Task();
						task = taskService.findTaskByTaskId(taskId);
						task.setExecuteTime(DateUtils.dateToString(task.getExecute_time()));
						task.setBeginTime(DateUtils.dateToString(task.getBegin_time()));
						task.setEndTime(DateUtils.dateToString(task.getEnd_time()));
                        task.setGroupFlag(DateUtils.dateToString(task.getGroup_flag()));
						if(task!=null){
							net.sf.json.JSONObject taskObject = new net.sf.json.JSONObject().fromObject(task);
							json.put("taskObj", taskObject);
							json.put("result", "success");
							Respones r = new Respones();
							r.setState("200");//????????????
							net.sf.json.JSONArray state = new net.sf.json.JSONArray().fromObject(r);
							json.put("state", state);
						}else{
							Respones r = new Respones();
							r.setState("421");//???????????????
							net.sf.json.JSONArray state = new net.sf.json.JSONArray().fromObject(r);
							json.put("state", state);
						}
					}else{
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("orderId", orderId);
						List t= taskService.findTaskByOrderId(param);
	//					if(t.size()>0){
	//						t.setExecuteTime(DateUtils.dateToString(t.getExecute_time()));
	//						t.setBeginTime(DateUtils.dateToString(t.getBegin_time()));
	//						t.setEndTime(DateUtils.dateToString(t.getEnd_time()));
	//					}
						
						
						JSONArray taskObject = new JSONArray().fromObject(t);
						json.put("code", 200);
						json.put("status", order.getStatus());
//								json.put("websoc", order.getWebsoc());
						json.put("taskObj", taskObject);
					}
					
				}else{
					json.put("code", 421);
					json.put("message", "???????????????");
				}
				this.countAPI(token, orderId, null, 5, 3);
				
			}else{
				json.put("code", 422);
				json.put("message", "token??????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//??????404????????????
			json.put("message", "??????status??????");
		}
        return json.toString();
    }
	
	
	//????????????
	@GET
    @Path("/orderResult/{orderId}/{taskId}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public String VulnScan_Get_orderResult(@PathParam("token") String token,@PathParam("orderId") String orderId, @PathParam("taskId") String taskId) {
		JSONObject json = new JSONObject();
		try {
			//??????token??????user
			User user = userService.findUserByToken(token);
			if(token!=null && token!="" && user!=null){
				Order order = orderService.findOrderByOrderId(orderId);
				//??????token?????? add by tangtang 2016-9-21
				boolean apikey = false;
				boolean userId = false;
				if(order.getApiKey()!=null){
					apikey = order.getApiKey().equals(user.getApikey());
				}
				if(String.valueOf(order.getUserId())!=null){
					userId = String.valueOf(order.getUserId()).equals(String.valueOf(user.getId()));
				}
				if(order!=null && order.getServiceId()==5 && (apikey||userId)){
					//taskId ?????????????????????????????????????????????
					if(taskId!=null && taskId!=""){
						Task t = taskService.findTaskByTaskId(taskId);
						String orderTaskId = t.getOrderTaskId();
						String[] strs = orderTaskId.split("_");  	
						String oId = strs[1];
						if(orderId.equals(oId)){
							List<TaskWarn> taskwarnList = taskWarnService.findTaskWarnByTaskId(Integer.parseInt(taskId));
							JSONArray taskwarnObj = new JSONArray().fromObject(taskwarnList);
							json.put("code", 200);
							json.put("taskwarnObj", taskwarnObj);
						}else{
							json.put("code", 431);
							json.put("message", "??????taskId??????????????????");
						}
					}
				}else{
					json.put("code", 421);
					json.put("message", "???????????????");
				}
				this.countAPI(token, orderId, taskId, 5, 4);
			}else{
				json.put("code", 422);
				json.put("message", "token??????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 404);//??????404????????????
			json.put("message", "??????????????????");
		}
		return json.toString();
    }
	
	//add by tangxr 2016-9-5 ???????????????????????????
	public long getTask(Date begin_date,Date end_date,int scan_type){
		//??????????????????
        long times = 0;
		//??????
    	long ms = 0;//????????????????????????
    	Date bDate = begin_date;
    	Date eDate = end_date;
        ms = DateUtils.getMsByDays(bDate, eDate);
        
        int typeInt = scan_type;
        
        switch(5){
        case 3:
        	if(ms==0){
        		times = 1;//????????????????????????
        	}else if(typeInt==2){//30??????
	        	int min_30 = 1000*3600/2;
	        	if(ms%min_30 > 0){
	        		times = (long)(ms/min_30) + 1;
	        	}else{
	        		times = (long)(ms/min_30);
	        	}
        	}else if(typeInt==3){//1??????
        		int oneHour = 1000*3600;
	        	if(ms%oneHour > 0){
	        		times = (long)(ms/oneHour) + 1;
	        	}else{
	        		times = (long)(ms/oneHour);
	        	}
        	}else if(typeInt==4){//1???
        		int oneDay = 1000*3600*24;
	        	if(ms%oneDay > 0){
	        		times = (long)(ms/oneDay) + 1;
	        	}else{
		        	times = (long)(ms/oneDay);
	        	}
        	}
        	break;
        case 5:
        	if(ms==0){
        		times = 1;//????????????????????????
        	}else if(typeInt==1){//10??????
        		int min_10 = 1000*3600/6;
	        	if(ms%min_10 > 0){
	        		times = (long)(ms/min_10) + 1;
	        	}else{
	        		times = (long)(ms/min_10);
	        	}
        	}else if(typeInt==2){//30??????
        		int min_30 = 1000*3600/2;
	        	if(ms%min_30 > 0){
	        		times = (long)(ms/min_30) + 1;
	        	}else{
	        		times = (long)(ms/min_30);
	        	}
        	}else if(typeInt==3){//1??????
        		int oneHour = 1000*3600;
	        	if(ms%oneHour > 0){
	        		times = (long)(ms/oneHour) + 1;
	        	}else{
	        		times = (long)(ms/oneHour);
	        	}
        	}
        	break;
        }
		return times;
	}
	
	//add by tangxr 2016-8-25 ??????api
	public void countAPI(String token, String orderId, String taskId, int service_type, int api_type){
		User user = userService.findUserByToken(token);
		if(user.getType()!=3){
			Map<String, Object> param = new HashMap<String, Object>();
	        param.put("api_type", 1);
	        param.put("orderId", orderId);
	        API used = orderAPIService.findUsedByParam(param);
			
			//insert????????????
			APINum num = new APINum();
			num.setApikey(user.getApikey());
			num.setService_type(service_type);
			num.setApi_type(api_type);//1????????????2??????
			num.setStatus(1);
			num.setCreate_time(new Date());
			
			num.setApiId(used.getApiId());
			num.setToken(token);
			num.setOrderId(orderId);
			if(taskId!=null){
				num.setTaskId(Integer.parseInt(taskId));
			}
			userService.insertAPINum(num);
			ManagerWorker.createAPINum(user.getApikey(), service_type, api_type, 1);
		}
		//end
	}
		
	private URL base = this.getClass().getResource("");
	//??????report
	@POST
    @Path("/orderReport/{orderId}/{token}")
	public void VulnScan_Get_orderReport(@PathParam("token") String token,@PathParam("orderId") String orderId,@Context HttpServletRequest request,@Context HttpServletResponse response) {
//		JSONObject json = new JSONObject();
		try {
			//??????token??????user
			User user = userService.findUserByToken(token);
			//????????????
			Order order = orderService.findOrderByOrderId(orderId);
			//??????token?????? add by tangtang 2016-9-21
			boolean apikey = false;
			boolean userId = false;
			if(order.getApiKey()!=null){
				apikey = order.getApiKey().equals(user.getApikey());
			}
			if(String.valueOf(order.getUserId())!=null){
				userId = String.valueOf(order.getUserId()).equals(String.valueOf(user.getId()));
			}
			if(order!=null && order.getServiceId()==5 && (apikey||userId)){
				this.countAPI(token, orderId, null, 5, 5);
				
	            String group_flag = null;
	            String imgPie = null;
	            String imgBar = null;
	            String imgLine = null;
	            //??????????????????
	            List<OrderTask> otList = orderTaskService.findByOrderId(orderId);
	            Map<String, Object> paramMap = new HashMap<String, Object>();
	            paramMap.put("orderId", orderId);
	            paramMap.put("type", order.getType());
	            paramMap.put("count", otList.size());
	            paramMap.put("websoc", order.getWebsoc());
	            paramMap.put("group_flag", group_flag);
	            
                //??????????????????
                Map<String, String> map = this.getExportData(orderId,paramMap);
                
                String fileDir = new File(base.getFile(), "../../../../../../doc/").getCanonicalPath().replaceAll("%20", " ");
                
                System.out.println(fileDir+"        111111111"+base.getFile());
                //??????????????????
                
                OPCPackage opcPackage = POIXMLDocument.openPackage(fileDir+"/leak.docx");
                XWPFDocument hdt = new XWPFDocument(opcPackage);
                
                
                // ??????????????????????????????  
                Iterator<XWPFParagraph> itPara = hdt.getParagraphsIterator();
                while (itPara.hasNext()) {
                    XWPFParagraph paragraph = (XWPFParagraph) itPara.next();  
                    Set<String> set = map.keySet();  
                    Iterator<String> iterator = set.iterator();  
                    while (iterator.hasNext()) {  
                        String key = iterator.next();  
                        List<XWPFRun> run=paragraph.getRuns();
                         for(int i=0;i<run.size();i++)  
                         {  
                          if(run.get(i).getText(run.get(i).getTextPosition())!=null && run.get(i).getText(run.get(i).getTextPosition()).equals(key))  
                          {      
                            /**??????0?????????????????????????????????????????????????????????,?????????????????????0?????? 
                             * ????????????????????????????????????????????? 
                            * */           
                              run.get(i).setText(map.get(key),0);
                          }      
                         }      
                    }      
                } 
                
                // ??????????????????????????????  
                Iterator<XWPFTable> itTable = hdt.getTablesIterator();  
                while (itTable.hasNext()) {  
                    XWPFTable table = (XWPFTable) itTable.next();  
                    int rcount = table.getNumberOfRows();  
                    for (int i = 0; i < rcount; i++) {  
                        XWPFTableRow row = table.getRow(i);  
                        List<XWPFTableCell> cells = row.getTableCells();  
                        for (XWPFTableCell cell : cells) {  
                            for (Entry<String, String> e : map.entrySet()) {  
                                if (cell.getText().equals(e.getKey())) {  
                                    cell.removeParagraph(0);  
                                    cell.setText(e.getValue());  
                                }  
                            }  
                        }  
                    }  
                }
                
                XWPFParagraph paragraph21 = hdt.createParagraph();
                paragraph21.setStyle("2");  
                //???????????????????????????run,?????????run?????????????????????word???????????????????????????,?????????????????????,??????0?????????????????????????????????  
                XWPFRun run21 = paragraph21.insertNewRun(0);  
                //??????run??????   
                run21.setBold(true); 
                run21.setText("2.1 ????????????");
                //????????????
                /*String userName = System.getProperty("user.name");
                //??????????????????
                String filePath = request.getSession().getServletContext().getRealPath("/source/chart");
                File file = new File(filePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String fileName = filePath +"/"+ System.currentTimeMillis()+".png";
               
                createImage(request, response, fileName, imgPie);
                
                XWPFParagraph p = hdt.createParagraph();
                String blipId = p.getDocument().addPictureData(
                        new FileInputStream(new File(fileName)),
                        Document.PICTURE_TYPE_PNG);
                createPicture(blipId,
                        hdt.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 264, 312, p);*/
                
                
                XWPFParagraph paragraph22 = hdt.createParagraph();
                paragraph22.setStyle("2");  
                //???????????????????????????run,?????????run?????????????????????word???????????????????????????,?????????????????????,??????0?????????????????????????????????  
                XWPFRun run22 = paragraph22.insertNewRun(0);  
                //??????run??????   
                run22.setBold(true); 
                run22.setText("2.2 ????????????");
                
                //????????????
                /*String userName1 = System.getProperty("user.name");
                String filePath1 = request.getSession().getServletContext().getRealPath("/source/chart");
                File file1 = new File(filePath1);
                if(!file1.exists()){
                    file1.mkdir();
                }
                String fileName1 = filePath1 +"/"+ System.currentTimeMillis()+".png";
               
                createImage(request, response, fileName1, imgBar);
                
                XWPFParagraph p1 = hdt.createParagraph();
                String blipId1 = p1.getDocument().addPictureData(
                        new FileInputStream(new File(fileName1)),
                        Document.PICTURE_TYPE_PNG);
                createPicture(blipId1,
                        hdt.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 580, 332, p1);*/
                
                
                
                XWPFParagraph paragraph23 = hdt.createParagraph();
                paragraph23.setStyle("2");  
                //???????????????????????????run,?????????run?????????????????????word???????????????????????????,?????????????????????,??????0?????????????????????????????????  
                XWPFRun run23 = paragraph23.insertNewRun(0);  
                //??????run??????   
                run23.setBold(true); 
                run23.setText("2.3 ????????????");
                
                if(order.getType()==1){
                    //????????????
                    /*String userName2 = System.getProperty("user.name");
                    String filePath2 = request.getSession().getServletContext().getRealPath("/source/chart");
                    File file2 = new File(filePath2);
                    if(!file2.exists()){
                        file2.mkdir();
                    }
                    String fileName2 = filePath2 +"/"+ System.currentTimeMillis()+".png";
                   
                    createImage(request, response, fileName2, imgLine);
                    
                    XWPFParagraph p2 = hdt.createParagraph();
                    String blipId2 = p2.getDocument().addPictureData(
                            new FileInputStream(new File(fileName2)),
                            Document.PICTURE_TYPE_PNG);
                    createPicture(blipId2,
                            hdt.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 580, 332, p2);*/
                }else{
                    XWPFParagraph para = hdt.createParagraph();
                    //???????????????????????????run,?????????run?????????????????????word???????????????????????????,?????????????????????,??????0?????????????????????????????????  
                    XWPFRun run = para.insertNewRun(0);  
                    //??????run??????   
                    run.setText("???");
                }
                
                
                XWPFParagraph paragraph24 = hdt.createParagraph();
                paragraph24.setStyle("2");  
                //???????????????????????????run,?????????run?????????????????????word???????????????????????????,?????????????????????,??????0?????????????????????????????????  
                XWPFRun run24 = paragraph24.insertNewRun(0);  
                //??????run??????   
                run24.setBold(true); 
                run24.setText("2.4 ????????????");
                
                XWPFTable newt = hdt.createTable();
                //??????TblW???type?????????STTblWidth.AUTO,???????????????????????????????????????????????????STTblWidth.DXA
                CTTblPr tlPr = newt.getCTTbl().getTblPr();
                tlPr.getTblW().setType(STTblWidth.DXA);
                tlPr.getTblW().setW(new BigInteger("8500")); 
                
                XWPFTableRow rowr = newt.getRow(0);
                
                XWPFTableCell ccrr = rowr.getCell(0);
                
                XWPFParagraph ccprr=ccrr.getParagraphs().get(0);
                XWPFRun ccpr1 = ccprr.createRun();
                ccpr1.setFontSize(10);
                ccpr1.setBold(true);
                ccpr1.setColor("000000");
                ccpr1.setText("????????????");
                
                
                XWPFTableCell c12 =rowr.createCell();
                XWPFParagraph ccp12=c12.getParagraphs().get(0);
                XWPFRun ccpr12 = ccp12.createRun();
                ccpr12.setFontSize(10);
                ccpr12.setBold(true);
                ccpr12.setColor("000000");
                ccpr12.setText("????????????");
                
                XWPFTableCell c13 =rowr.createCell();
                XWPFParagraph ccp13=c13.getParagraphs().get(0);
                XWPFRun ccpr13 = ccp13.createRun();
                ccpr13.setFontSize(10);
                ccpr13.setBold(true);
                ccpr13.setColor("000000");
                ccpr13.setText("????????????");
               
                XWPFTableRow two22 = newt.createRow();
                two22.getCell(0).setText("1");
                two22.getCell(1).setText("2");
                two22.getCell(2).setText("3");
                
                //??????????????????
                insertDataToTable(newt,2,true,1,paramMap);
                
                
                XWPFParagraph paragraph3 = hdt.createParagraph();
                paragraph3.setStyle("1");  
                //???????????????????????????run,?????????run?????????????????????word???????????????????????????,?????????????????????,??????0?????????????????????????????????  
                XWPFRun run3 = paragraph3.insertNewRun(0);  
                //??????run??????   
                run3.setBold(true); 
                run3.setText("3. ????????????????????????");
                
                XWPFParagraph paragraph31 = hdt.createParagraph();
                paragraph31.setStyle("2");  
                //???????????????????????????run,?????????run?????????????????????word???????????????????????????,?????????????????????,??????0?????????????????????????????????  
                XWPFRun run31 = paragraph31.insertNewRun(0);  
                //??????run??????   
                run31.setBold(true); 
                run31.setText("3.1 ??????????????????");
                
                XWPFTable newtable = hdt.createTable();
                //??????TblW???type?????????STTblWidth.AUTO,???????????????????????????????????????????????????STTblWidth.DXA
                CTTblPr tPr = newtable.getCTTbl().getTblPr();
                tPr.getTblW().setType(STTblWidth.DXA);
                tPr.getTblW().setW(new BigInteger("8500")); 
                
                XWPFTableRow row = newtable.getRow(0);
                
                XWPFTableCell cc = row.getCell(0);
                cc.setColor("285ea1");
                
                XWPFParagraph ccp=cc.getParagraphs().get(0);
                XWPFRun ccpr = ccp.createRun();
                ccpr.setFontSize(10);
                ccpr.setBold(true);
                ccpr.setColor("ffffff");
                ccpr.setText("????????????");
                
                row.createCell().setText("");
                
                XWPFTableCell c1 =row.createCell();
                c1.setColor("285ea1");
                XWPFParagraph ccp1=c1.getParagraphs().get(0);
                XWPFRun ccpr0 = ccp1.createRun();
                ccpr0.setFontSize(10);
                ccpr0.setBold(true);
                ccpr0.setColor("ffffff");
                ccpr0.setText("????????????");
               
                XWPFTableRow two2 = newtable.createRow();
                two2.getCell(0).setText("1");
                two2.getCell(1).setText("2");
                
                mergeCellsHorizontal(newtable, 0, 0, 1);
                
                //??????????????????
                insertDataToTable(newtable,2,true,2,paramMap);
                
                
                XWPFParagraph paragraph32 = hdt.createParagraph();
                paragraph32.setStyle("2");  
                //???????????????????????????run,?????????run?????????????????????word???????????????????????????,?????????????????????,??????0?????????????????????????????????  
                XWPFRun run32 = paragraph32.insertNewRun(0);  
                //??????run??????   
                run32.setBold(true); 
                run32.setText("3.2 ???????????????????????????");           
                
                
                List result = alarmService.findAlarmByOrderIdorGroupId(paramMap);
                //????????????
                int index = 1;
                //????????????
                String advice ="";
                for (int i = 0; i < result.size(); i++) {
                    XWPFParagraph paragraph = hdt.createParagraph();
                    List<String> list = new ArrayList<String>();
                    Alarm alarm = (Alarm) result.get(i);
                    String name = (String) alarm.getName();
                
                    paragraph.setStyle("3");  
                    //???????????????????????????run,?????????run?????????????????????word???????????????????????????,?????????????????????,??????0?????????????????????????????????  
                    XWPFRun run = paragraph.insertNewRun(0);  
                    //??????run??????   
                    run.setBold(true); 
                    run.setText("3.2."+(index)+" "+name);
                    index++;
                    
                    paramMap.put("name", name);
                    paramMap.put("level", null);
                    List<Alarm> alarmList = alarmService.findAlarmByOrderId(paramMap);
                    for (int j = 0; j < alarmList.size(); j++) {
                        advice = alarmList.get(j).getAdvice();
                        XWPFTable tableOne = hdt.createTable();
                        //??????TblW???type?????????STTblWidth.AUTO,???????????????????????????????????????????????????STTblWidth.DXA
                        CTTblPr tblPr = tableOne.getCTTbl().getTblPr();
                        tblPr.getTblW().setType(STTblWidth.DXA);
                        tblPr.getTblW().setW(new BigInteger("8500")); 
                        
                        XWPFTableRow tableOneRowOne = tableOne.getRow(0);
                        XWPFTableCell cell = tableOneRowOne.getCell(0);
                        cell.setColor("285ea1");
                        
                        XWPFParagraph cellP=cell.getParagraphs().get(0);
                        XWPFRun cellR = cellP.createRun();
                        cellR.setFontSize(10);
                        cellR.setBold(run.isBold());
                        cellR.setColor("ffffff");
                        cellR.setText(name+(j+1)+"/"+alarmList.size());
                        
                        XWPFTableRow tableOneRowTwo = tableOne.createRow();
                        String levelName = "";
                        if(alarmList.get(j).getLevel()==0){
                            levelName = "???";
                        }else if(alarmList.get(j).getLevel()==1){
                            levelName = "???";
                        }else if(alarmList.get(j).getLevel()==2){
                            levelName = "???";
                        }
                        XWPFTableCell cell2 = tableOneRowTwo.getCell(0);
                        XWPFParagraph cellP2=cell2.getParagraphs().get(0);
                        XWPFRun cellR2 = cellP2.createRun();
                        cellR2.setFontSize(10);
                        cellR2.setText("????????????"+levelName);
                        
                        XWPFTableRow tableOneRow3 = tableOne.createRow();
                        XWPFTableCell cell3 = tableOneRow3.getCell(0);
                        XWPFParagraph cellP3=cell3.getParagraphs().get(0);
                        XWPFRun cellR3 = cellP3.createRun();
                        cellR3.setFontSize(10);
                        cellR3.setText("URL???"+alarmList.get(j).getAlarm_content());
                        
                        XWPFTableRow tableOneRow4 = tableOne.createRow();
                        XWPFTableCell cell4 = tableOneRow4.getCell(0);
                        XWPFParagraph cellP4=cell4.getParagraphs().get(0); 
                        XWPFRun cellR4 = cellP4.createRun();
                        cellR4.setFontSize(10); 
                        cellR4.setText("???????????????");
                        cellR4.addBreak();
                        
                        XWPFRun r = cellP4.createRun();
                        r.setFontSize(10);
                        r.setColor("285ea1");
                        r.setText(alarmList.get(j).getKeyword());
                        r.addBreak();
                        
                        hdt.createParagraph();
                    }
                    //????????????
                    XWPFParagraph detailsp = hdt.createParagraph();
                    detailsp.setStyle("3");
                    //???????????????????????????run,?????????run?????????????????????word???????????????????????????,?????????????????????,??????0?????????????????????????????????  
                    XWPFRun rundetails = detailsp.insertNewRun(0);  
                    //??????run??????   
                    rundetails.setBold(true); 
                    rundetails.setText("3.2."+(index)+" "+name+"????????????"); 
                    index++;
                    
                    XWPFTable tabledetails = hdt.createTable();
                    //??????TblW???type?????????STTblWidth.AUTO,???????????????????????????????????????????????????STTblWidth.DXA
                    CTTblPr tblPr = tabledetails.getCTTbl().getTblPr();
                    tblPr.getTblW().setType(STTblWidth.DXA);
                    tblPr.getTblW().setW(new BigInteger("8500")); 
                    XWPFTableRow tabledetailsRowOne = tabledetails.getRow(0);
                    XWPFTableCell c = tabledetailsRowOne.getCell(0);
                    XWPFParagraph cP=c.getParagraphs().get(0);
                    XWPFRun cR = cP.createRun();
                    String[] advices = advice.split("\r\n");
                    
                    for(String text : advices){
                        XWPFRun r = cP.createRun();
                        r.setFontSize(9);
                        r.setText(text.trim());
                        r.addBreak();
                    }
                    
                }

                
                //??????word??????????????????????????????
                response.reset();
                response.setContentType("application/x-msdownload");
                String outfile = orderId+"????????????-??????.docx";
                
                String userAgent = request.getHeader("User-Agent");
                String new_filename = URLEncoder.encode(outfile, "UTF8");
                // ????????????UA??????????????????IE????????????????????????????????????IE??????????????????
                String rtn = "filename=\"" + new_filename + "\"";
                if (userAgent != null) {
                    userAgent = userAgent.toLowerCase();
                    // IE????????????????????????URLEncoder??????
                    if (userAgent.indexOf("msie") != -1) {
                        rtn = "filename=\"" + new_filename + "\"";
                    }
                    // Opera?????????????????????filename*
                    else if (userAgent.indexOf("opera") != -1) {
                        rtn = "filename*=UTF-8''" + new_filename;
                    }
                    // Safari????????????????????????ISO?????????????????????
                    else if (userAgent.indexOf("safari") != -1) {
                        rtn = "filename=\""
                                + new String(outfile.getBytes("UTF-8"), "ISO8859-1")
                                + "\"";
                    }
                    // Chrome????????????????????????MimeUtility?????????ISO?????????????????????
                    else if (userAgent.indexOf("applewebkit") != -1) {
                        new_filename = MimeUtility.encodeText(outfile, "UTF8", "B");
                        rtn = "filename=\"" + new_filename + "\"";
                    }
                    // FireFox????????????????????????MimeUtility???filename*???ISO?????????????????????
                    else if (userAgent.indexOf("mozilla") != -1) {
                        rtn = "filename*=UTF-8''" + new_filename;
                    }
                } 
                response.addHeader("Content-Disposition", "attachment;"+rtn);
                ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                ServletOutputStream servletOS = response.getOutputStream();
                hdt.write(ostream);
                servletOS.write(ostream.toByteArray());
                servletOS.flush();
                servletOS.close();
	                
//				json.put("code", 200);
//				json.put("iofile", ostream.toByteArray());

			}else{
//				json.put("code", 421);
//				json.put("message", "???????????????");
				System.out.println("???????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			json.put("code", 404);//??????404????????????
//			json.put("message", "????????????????????????");
			System.out.println("????????????????????????");
		}
//        return HttpDownloader.download( rtn, 0, request);
    }
	
	
	private void insertDataToTable(XWPFTable newtable,int tableSize,boolean isDelTmpRow,int tableIndex, Map<String, Object> paramMap) throws Exception {
        List<List<String>> resultList = generateData(tableIndex,paramMap);
        insertValueToNewTable(newtable,resultList,tableSize,isDelTmpRow,tableIndex);
        
    }
    
    
    /**
     * @Description: ??????????????????????????????,??????????????????????????????(????????????)???????????????????????????(??????????????? ?????? ?????? ??????)
     * @param resultList ????????????
     * @param tableRowSize ?????????????????? ???????????????????????????????????????????????????
     * @param isDelTmpRow ?????????????????????
     */
    //TODO ???????????????????????????????????????????????????????????????
    public void insertValueToTable(XWPFDocument doc,
        List<List<String>> resultList,int tableRowSize,boolean isDelTmpRow,int tableIndex) throws Exception {
//      Iterator<XWPFTable> iterator = doc.getTablesIterator();
      XWPFTable table = doc.getTableArray(tableIndex);
//      XWPFTable table = null;
      List<XWPFTableRow> rows=null;
      List<XWPFTableCell> cells=null;
      List<XWPFTableCell> tmpCells=null;//?????????
      XWPFTableRow tmpRow=null;//?????????
      XWPFTableCell tmpCell=null;//?????????
      boolean flag=false;//??????????????????
//      while (iterator.hasNext()) {
//        table = iterator.next();
        rows = table.getRows();
//        if(rows.size()==tableRowSize){
          tmpRow=rows.get(tableRowSize-1);
          cells =tmpRow.getTableCells();
//          if(cells.size()==resultList.get(0).size()){
//            flag=true;
//            break;
//          }
//        }
//      }
//      if(!flag){
//        return;
//      }
      tmpCells=tmpRow.getTableCells();
      for(int i=0,len=resultList.size();i<len;i++){
        XWPFTableRow row=table.createRow();
        row.setHeight(tmpRow.getHeight());
        List<String> list=resultList.get(i);
        cells=row.getTableCells();
        //??????????????????????????????????????????????????????
        for(int k=0,klen=cells.size();k<klen;k++){
          if(tableIndex==2){
              XWPFTableCell cell = null;
              cell=cells.get(k);
              if(list.get(0).equals("???")){
                  cells.get(0).setColor("ff0000");
              }else if(list.get(0).equals("???")){
                  cells.get(0).setColor("ffc100");
              }if(list.get(0).equals("???")){
                  cells.get(0).setColor("ffff00");
              }
              tmpCell=tmpCells.get(k);
              setCellText(tmpCell, cell, list.get(k));
          }else{
              tmpCell=tmpCells.get(k);
              XWPFTableCell cell=cells.get(k);
              setCellText(tmpCell, cell, list.get(k));
          }
          
        }
        //?????????????????????
        for(int j=cells.size(),jlen=list.size();j<jlen;j++){
          tmpCell=tmpCells.get(j);
          XWPFTableCell cell=row.addNewTableCell();
            setCellText(tmpCell, cell, list.get(j));
        }
      }
      //???????????????
      if(isDelTmpRow){
        if(tableIndex==1){
            table.removeRow(tableRowSize-1);
        }else if(tableIndex==2){
            table.removeRow(tableRowSize-1);
            table.removeRow(tableRowSize-1);
            table.removeRow(tableRowSize-1);
        }
      }
    }
    
    
  //TODO ???????????????????????????????????????????????????????????????
    public void insertValueToNewTable(XWPFTable newtable,
            List<List<String>> resultList,int tableRowSize,boolean isDelTmpRow,int tableIndex) throws Exception {
//          Iterator<XWPFTable> iterator = doc.getTablesIterator();
          XWPFTable table = newtable;
//          XWPFTable table = null;
          List<XWPFTableRow> rows=null;
          List<XWPFTableCell> cells=null;
          List<XWPFTableCell> tmpCells=null;//?????????
          XWPFTableRow tmpRow=null;//?????????
          XWPFTableCell tmpCell=null;//?????????
          boolean flag=false;//??????????????????
//          while (iterator.hasNext()) {
//            table = iterator.next();
            rows = table.getRows();
//            if(rows.size()==tableRowSize){
              tmpRow=rows.get(tableRowSize-1);
              cells =tmpRow.getTableCells();
//              if(cells.size()==resultList.get(0).size()){
//                flag=true;
//                break;
//              }
//            }
//          }
//          if(!flag){
//            return;
//          }
          tmpCells=tmpRow.getTableCells();
          for(int i=0,len=resultList.size();i<len;i++){
            XWPFTableRow row=table.createRow();
            row.setHeight(tmpRow.getHeight());
            List<String> list=resultList.get(i);
            cells=row.getTableCells();
            //??????????????????????????????????????????????????????
            for(int k=0,klen=cells.size();k<klen;k++){
              if(tableIndex==2){
                  XWPFTableCell cell = null;
                  cell=cells.get(k);
                  if(list.get(0).equals("???")){
                      cells.get(0).setColor("ff0000");
                  }else if(list.get(0).equals("???")){
                      cells.get(0).setColor("ffc100");
                  }if(list.get(0).equals("???")){
                      cells.get(0).setColor("ffff00");
                  }
                  tmpCell=tmpCells.get(k);
                  setCellText(tmpCell, cell, list.get(k));
              }else{
                  tmpCell=tmpCells.get(k);
                  XWPFTableCell cell=cells.get(k);
                  setCellText(tmpCell, cell, list.get(k));
              }
              
            }
            //?????????????????????
            for(int j=cells.size(),jlen=list.size();j<jlen;j++){
              tmpCell=tmpCells.get(j);
              XWPFTableCell cell=row.addNewTableCell();
                setCellText(tmpCell, cell, list.get(j));
            }
          }
          //???????????????
          if(isDelTmpRow){
              table.removeRow(tableRowSize-1);
          }
        }
    
    public  void setCellText(XWPFTableCell tmpCell,XWPFTableCell cell,String text) throws Exception{
      CTTc cttc2 = tmpCell.getCTTc();
      CTTcPr ctPr2=cttc2.getTcPr();
      
      CTTc cttc = cell.getCTTc();
      CTTcPr ctPr = cttc.addNewTcPr();
//      cell.setColor(tmpCell.getColor());
//      cell.setVerticalAlignment(tmpCell.getVerticalAlignment());
//      if(ctPr2.getTcW()!=null){
//        ctPr.addNewTcW().setW(ctPr2.getTcW().getW());
//      }
//      if(ctPr2.getVAlign()!=null){
//        ctPr.addNewVAlign().setVal(ctPr2.getVAlign().getVal());
//      }
      if(cttc2.getPList().size()>0){
        CTP ctp=cttc2.getPList().get(0);
        if(ctp.getPPr()!=null){
          if(ctp.getPPr().getJc()!=null){
            cttc.getPList().get(0).addNewPPr().addNewJc().setVal(ctp.getPPr().getJc().getVal());
          }
        }
      }
      
      
//      if(ctPr2.getTcBorders()!=null){
//          ctPr.setTcBorders(ctPr2.getTcBorders());
//      }
      
      XWPFParagraph tmpP=tmpCell.getParagraphs().get(0);
      XWPFParagraph cellP=cell.getParagraphs().get(0);
      XWPFRun tmpR =null;
      if(tmpP.getRuns()!=null&&tmpP.getRuns().size()>0){
        tmpR=tmpP.getRuns().get(0);
      }
      XWPFRun cellR = cellP.createRun();
      cellR.setText(text);
      //??????????????????
      if(tmpR!=null){
        cellR.setBold(tmpR.isBold());
        cellR.setItalic(tmpR.isItalic());
        cellR.setStrike(tmpR.isStrike());
        cellR.setUnderline(tmpR.getUnderline());
          cellR.setColor(tmpR.getColor());
        cellR.setTextPosition(tmpR.getTextPosition());
        if(tmpR.getFontSize()!=-1){
          cellR.setFontSize(tmpR.getFontSize());
        }
        if(tmpR.getFontFamily()!=null){
          cellR.setFontFamily(tmpR.getFontFamily());
        }
        if(tmpR.getCTR()!=null){
          if(tmpR.getCTR().isSetRPr()){
            CTRPr tmpRPr =tmpR.getCTR().getRPr();
            if(tmpRPr.isSetRFonts()){
              CTFonts tmpFonts=tmpRPr.getRFonts();
              CTRPr cellRPr=cellR.getCTR().isSetRPr() ? cellR.getCTR().getRPr() : cellR.getCTR().addNewRPr();
              CTFonts cellFonts = cellRPr.isSetRFonts() ? cellRPr.getRFonts() : cellRPr.addNewRFonts();
              cellFonts.setAscii(tmpFonts.getAscii());
              cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());
              cellFonts.setCs(tmpFonts.getCs());
              cellFonts.setCstheme(tmpFonts.getCstheme());
              cellFonts.setEastAsia(tmpFonts.getEastAsia());
              cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());
              cellFonts.setHAnsi(tmpFonts.getHAnsi());
              cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());
            }
          }
        }
      }
      //??????????????????
      cellP.setAlignment(tmpP.getAlignment());
      cellP.setVerticalAlignment(tmpP.getVerticalAlignment());
      cellP.setBorderBetween(tmpP.getBorderBetween());
      cellP.setBorderBottom(tmpP.getBorderBottom());
      cellP.setBorderLeft(tmpP.getBorderLeft());
      cellP.setBorderRight(tmpP.getBorderRight());
      cellP.setBorderTop(tmpP.getBorderTop());
      cellP.setPageBreak(tmpP.isPageBreak());
      if(tmpP.getCTP()!=null){
        if(tmpP.getCTP().getPPr()!=null){
          CTPPr tmpPPr = tmpP.getCTP().getPPr();
          CTPPr cellPPr = cellP.getCTP().getPPr() != null ? cellP.getCTP().getPPr() : cellP.getCTP().addNewPPr();
          //????????????????????????
          CTSpacing tmpSpacing =tmpPPr.getSpacing();
          if(tmpSpacing!=null){
            CTSpacing cellSpacing= cellPPr.getSpacing()!=null?cellPPr.getSpacing():cellPPr.addNewSpacing();
            if(tmpSpacing.getAfter()!=null){
              cellSpacing.setAfter(tmpSpacing.getAfter());
            }
            if(tmpSpacing.getAfterAutospacing()!=null){
              cellSpacing.setAfterAutospacing(tmpSpacing.getAfterAutospacing());
            }
            if(tmpSpacing.getAfterLines()!=null){
              cellSpacing.setAfterLines(tmpSpacing.getAfterLines());
            }
            if(tmpSpacing.getBefore()!=null){
              cellSpacing.setBefore(tmpSpacing.getBefore());
            }
            if(tmpSpacing.getBeforeAutospacing()!=null){
              cellSpacing.setBeforeAutospacing(tmpSpacing.getBeforeAutospacing());
            }
            if(tmpSpacing.getBeforeLines()!=null){
              cellSpacing.setBeforeLines(tmpSpacing.getBeforeLines());
            }
            if(tmpSpacing.getLine()!=null){
              cellSpacing.setLine(tmpSpacing.getLine());
            }
            if(tmpSpacing.getLineRule()!=null){
              cellSpacing.setLineRule(tmpSpacing.getLineRule());
            }
          }
          //????????????????????????
          CTInd tmpInd=tmpPPr.getInd();
          if(tmpInd!=null){
            CTInd cellInd=cellPPr.getInd()!=null?cellPPr.getInd():cellPPr.addNewInd();
            if(tmpInd.getFirstLine()!=null){
              cellInd.setFirstLine(tmpInd.getFirstLine());
            }
            if(tmpInd.getFirstLineChars()!=null){
              cellInd.setFirstLineChars(tmpInd.getFirstLineChars());
            }
            if(tmpInd.getHanging()!=null){
              cellInd.setHanging(tmpInd.getHanging());
            }
            if(tmpInd.getHangingChars()!=null){
              cellInd.setHangingChars(tmpInd.getHangingChars());
            }
            if(tmpInd.getLeft()!=null){
              cellInd.setLeft(tmpInd.getLeft());
            }
            if(tmpInd.getLeftChars()!=null){
              cellInd.setLeftChars(tmpInd.getLeftChars());
            }
            if(tmpInd.getRight()!=null){
              cellInd.setRight(tmpInd.getRight());
            }
            if(tmpInd.getRightChars()!=null){
              cellInd.setRightChars(tmpInd.getRightChars());
            }
          }
        }
      }
    }
    
  //????????????
    public List<List<String>> generateData(int tableIndex, Map<String, Object> paramMap) {
      List<List<String>> resultList = new ArrayList<List<String>>();
      List result = alarmService.findAlarmByOrderIdorGroupId(paramMap);
      for (int i = 0; i < result.size(); i++) {
        List<String> list = new ArrayList<String>();
        Alarm alarm = (Alarm) result.get(i);
        String name = (String) alarm.getName();
        int num = alarm.getNum();
        int level = alarm.getLevel();
        if(tableIndex==1){
            list.add(name);
            list.add("0");
            list.add(String.valueOf(num));
        }else if(tableIndex==2){
            String levelName = "";
            if(level==0){
                levelName = "???";
            }else if(level==1){
                levelName = "???";
            }else if(level==2){
                levelName = "???";
            }
            list.add(levelName);
            list.add(name);
            list.add(String.valueOf(num));
        }
        resultList.add(list);
      }
      return resultList;
    }

	
    private Map<String, String> getExportData(String orderId, Map<String, Object> paramMap) {
        //????????????
        Order order = orderService.findOrderByOrderId(orderId);
        //??????????????????
        List<OrderTask> otList = orderTaskService.findByOrderId(orderId);
        String webSite = "";
        String webName = "";
        for (OrderTask ot : otList) {
            webSite = webSite+ot.getUrl()+","+"\r\n";
            webName = webName+ot.getUrl()+","+"\r\n";
        }
        webSite = webSite.substring(0, webSite.length()-3);
        webName = webName.substring(0, webName.length()-3);
        
        SimpleDateFormat odf = new SimpleDateFormat("yyyy/MM/dd");//??????????????????
        String createDate = odf.format(new Date());
        
        SimpleDateFormat odf1 = new SimpleDateFormat("yyyy???MM???dd???");//??????????????????
        String createDate1 = odf1.format(new Date());
        
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("orderId", orderId);
//        paramMap.put("type", order.getType());
//        paramMap.put("count", assetList.size());
//        paramMap.put("websoc", order.getWebsoc());
        List<Alarm> alarmList = alarmService.findAlarmByOrderId(paramMap);
        
        //???
        paramMap.put("level", WarnType.LOWLEVEL.ordinal());
        List<Alarm> lowList = alarmService.findAlarmByOrderId(paramMap);
        //???
        paramMap.put("level", WarnType.MIDDLELEVEL.ordinal());
        List<Alarm> middleList = alarmService.findAlarmByOrderId(paramMap);
        //???
        paramMap.put("level", WarnType.HIGHLEVEL.ordinal());
        List<Alarm> highList = alarmService.findAlarmByOrderId(paramMap);
        String risk = "";
        if(highList.size()>=2){//?????????
            risk = "??????";
        }else if(highList.size()<=0&&middleList.size()<=0){//?????????
            risk = "??????";
        }else{//?????????
            risk = "??????";
        }
        
        Map<String, String> map = new HashMap<String, String>();
        
        if(order.getTask_date()!=null){
            order.setTask_datevo(( DateUtils.dateToString(order.getTask_date())));
        }
        map.put("$createDate$", createDate);
        map.put("$createDate1$", createDate1);
        map.put("$JCSJ$", order.getTask_datevo());
        map.put("$LEAKNUM$", String.valueOf(alarmList.size()));
        map.put("$RISK$", risk);
        map.put("$LOWNUM$", String.valueOf(lowList.size()));
        map.put("$MIDDLENUM$", String.valueOf(middleList.size()));
        map.put("$HIGHNUM$", String.valueOf(highList.size()));
        map.put("$webSite$", webSite);
        map.put("$webName$", webName);  
        map.put("$catalog$", "3.2.1 ??????");
        return map;

    }
	
    public void createPicture(String blipId, int id, int width, int height,
            XWPFParagraph paragraph) {
          final int EMU = 9525;
          width *= EMU;
          height *= EMU;
          // String blipId =
          // getAllPictures().get(id).getPackageRelationship().getId();
//          if (paragraph == null) {
//            paragraph = createParagraph();
//          }
          CTInline inline = paragraph.createRun().getCTR().addNewDrawing()
              .addNewInline();
          String picXml = ""
              + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
              + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
              + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
              + "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
              + id
              + "\" name=\"img_"
              + id
              + "\"/>"
              + "            <pic:cNvPicPr/>"
              + "         </pic:nvPicPr>"
              + "         <pic:blipFill>"
              + "            <a:blip r:embed=\""
              + blipId
              + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
              + "            <a:stretch>"
              + "               <a:fillRect/>"
              + "            </a:stretch>"
              + "         </pic:blipFill>"
              + "         <pic:spPr>"
              + "            <a:xfrm>"
              + "               <a:off x=\"0\" y=\"0\"/>"
              + "               <a:ext cx=\""
              + width
              + "\" cy=\""
              + height
              + "\"/>"
              + "            </a:xfrm>"
              + "            <a:prstGeom prst=\"rect\">"
              + "               <a:avLst/>"
              + "            </a:prstGeom>"
              + "         </pic:spPr>"
              + "      </pic:pic>"
              + "   </a:graphicData>" + "</a:graphic>";
          // CTGraphicalObjectData graphicData =
          // inline.addNewGraphic().addNewGraphicData();
          XmlToken xmlToken = null;
          try {
            xmlToken = XmlToken.Factory.parse(picXml);
          } catch (XmlException xe) {
            xe.printStackTrace();
          }
          inline.set(xmlToken);
          // graphicData.set(xmlToken);
          inline.setDistT(0);
          inline.setDistB(0);
          inline.setDistL(0);
          inline.setDistR(0);
          CTPositiveSize2D extent = inline.addNewExtent();
          extent.setCx(width);
          extent.setCy(height);
          CTNonVisualDrawingProps docPr = inline.addNewDocPr();
          docPr.setId(id);
          docPr.setName("docx_img_ " + id);
          docPr.setDescr("docx Picture");
        }
	
    public void createImage(HttpServletRequest request, HttpServletResponse response,
            String fileName, String data) 
            throws ServletException, IOException {
        try {
            String[] url = data.split(",");
            String u = url[1];
            // Base64??????
            byte[] b = new BASE64Decoder().decodeBuffer(u);
            // ????????????
            OutputStream out = new FileOutputStream(new File(fileName));
            out.write(b);
            out.flush();
            out.close();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Description: ????????????
     */
    public void mergeCellsHorizontal(XWPFTable table, int row, int fromCell,
        int toCell) {
      for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
        XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
        if (cellIndex == fromCell) {
          // The first merged cell is set with RESTART merge value
          getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.RESTART);
        } else {
          // Cells which join (merge) the first one,are set with CONTINUE
          getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.CONTINUE);
        }
      }
    }
    
    /**
     * 
     * @Description: ??????Cell???CTTcPr,??????????????????
     */
    public CTTcPr getCellCTTcPr(XWPFTableCell cell) {
      CTTc cttc = cell.getCTTc();
      CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
      return tcPr;
    }
    
	 
}
