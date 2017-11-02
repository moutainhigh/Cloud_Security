package com.cn.ctbri.webservice;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.spi.resource.Singleton;
/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-01-05
 * 描        述：  接收安恒WebService请求接口
 * 版        本：  1.0
 */
@Singleton
@Component
@Path("arnhem")
public class ArnhemWebService {
	//日志对象
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
    private ITaskService taskService;
	@Autowired
    private IOrderService orderService;
	@Autowired
    private IAssetService assetService;
	@Autowired
    private IAlarmService alarmService;
	
	/**
	 * 功能描述： 解析reportXml,将扫描记录保存到数据库
	 * 参数描述：String reportXml 
	 * @throws DocumentException 
	 *		 @time 2015-01-05
	 */
	private void parseReportToDB(String reportXml) throws DocumentException {
		if(reportXml != null){
			//解析XML为文档对象
			Document document = DocumentHelper.parseText(reportXml);
			//
		}
	}

	
	//websoc推送
    @POST
    @Path("websocGetWarn")
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.TEXT_HTML)
    public String websocGetWarn(@Context HttpServletRequest request,@Context HttpServletResponse response) {
        try {
            log.info("[任务推送]:任务推送-解析任务推送告警发生异常!");
            JSONObject jsStr = JSONObject.fromObject(response);
            System.out.println(jsStr);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("[任务推送]:任务推送-解析任务推送告警发生异常!");
            throw new RuntimeException("[任务推送]:任务推送-解析任务推送告警发生异常!");
        }
        return "<Result value=\"Success\"></Result>";
    }
	
	
	//主动告警
	@POST
    @Path("arnhemGetWarn")
	@Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_XML)
    public String arnhemGetWarn(@Context HttpServletRequest request,@Context HttpServletResponse response) {
	    response.setContentType("Application/xml");
        try {
            ServletInputStream io = request.getInputStream();
            //判断流对象是否存在
            if(io != null){
                //创建流读取对象
                SAXReader reader = new SAXReader();
                //读取流封装为文档对象
                Document document = reader.read(io);
                System.err.println(document.asXML());
//              String str = request.getParameter("xmlstr");
//              decode = URLDecoder.decode(str, "UTF-8");
//              Document document = DocumentHelper.parseText(decode);
                Element task = document.getRootElement();
                String ss = task.getTextTrim();
                String cat1 = URLDecoder.decode(task.element("CAT1").getTextTrim(),"UTF-8");
                String cat2 = URLDecoder.decode(task.element("CAT2").getTextTrim(),"UTF-8");
                String name = URLDecoder.decode(task.element("NAME").getTextTrim(),"UTF-8");
                String severity = task.element("SEVERITY").getTextTrim();
                String rule = URLDecoder.decode(task.element("RULE").getTextTrim(),"UTF-8");
                String ct = task.element("CT").getTextTrim();
                String app_p = URLDecoder.decode(task.element("APP_P").getTextTrim(),"UTF-8");
                String tran_p = URLDecoder.decode(task.element("TRAN_P").getTextTrim(),"UTF-8");
                String url = URLDecoder.decode(task.element("URL").getTextTrim(),"UTF-8");
                String msg = URLDecoder.decode(task.element("MSG").getTextTrim(),"UTF-8");
                String task_id = task.element("TASK_ID").getTextTrim();
                System.out.println("cat1:"+cat1+";name:"+name+";severity:"+severity);
                //根据taskId查询地区
//        		int districtId = taskService.findDistrictIdByTaskId(task_id);
                TaskWarn taskwarn = new TaskWarn();
                taskwarn.setCat1(cat1);
                taskwarn.setCat2(cat2);
                taskwarn.setName(name);
                taskwarn.setSeverity(Integer.parseInt(severity));
                taskwarn.setRule(rule);
                taskwarn.setCt(Integer.parseInt(ct));
                taskwarn.setApp_p(app_p);
                taskwarn.setTran_p(tran_p);
                taskwarn.setUrl(url);
                taskwarn.setMsg(msg);
                taskwarn.setTask_id(task_id);
                taskwarn.setWarn_time(new Date());
                taskwarn.setServiceId(5);
//                taskwarn.setDistrictId(districtId);
//                ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//              taskService = (ITaskService)context.getBean("taskService");
                taskService.insertTaskWarn(taskwarn);
                
                Task t = new Task();
                t.setTaskId(Integer.parseInt(task_id));
                List<Asset> asset = assetService.findByTask(t);
                //更新地域告警数
                /*Map<String, Object> disMap = new HashMap<String, Object>();
				disMap.put("id", asset.get(0).getDistrictId());
				disMap.put("count", 1);
				disMap.put("serviceId", 5);
                alarmService.updateDistrict(disMap);
                System.out.println("999999");*/
                log.info("[任务主动告警]:任务-[" + task_id + "]完成入库!");
                //发短信
                /*if(cat2.equals("断网")){
                    List<Order> oList = orderService.findOrderByTask(t);
                    Order order=oList.get(0);
                    List<Linkman> mlist= orderService.findLinkmanById(order.getContactId());
                    Linkman linkman=mlist.get(0);
                    String phoneNumber = linkman.getMobile();//联系方式
                    String assetName = asset.get(0).getName();
                    if(!phoneNumber.equals("") && phoneNumber!=null){
                      //发短信
                        SMSUtils smsUtils = new SMSUtils();
                        smsUtils.sendMessage_warn(phoneNumber,order,assetName,null);
                        order.setMessage(1);
                        orderService.update(order);
                    }
                }*/
                
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("[任务主动告警]:任务主动告警-解析任务主动告警发生异常!");
            throw new RuntimeException("[任务主动告警]:任务主动告警-解析任务主动告警发生异常!");
        }
        return "<Result value=\"Success\"></Result>";
    }
	
	
	//华为告警解析
	@POST
    @Path("huaweiGetWarn")
	@Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_XML)
    public String huaweiGetWarn(@Context HttpServletRequest request,@Context HttpServletResponse response) {
	    response.setContentType("Application/xml");
        try {
            ServletInputStream io = request.getInputStream();
            //判断流对象是否存在
            if(io != null){
                //创建流读取对象
                SAXReader reader = new SAXReader();
                //读取流封装为文档对象
                Document document = reader.read(io);
                System.err.println(document);
//                log.info("[任务主动告警]:任务-[" + task_id + "]完成入库!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("[任务主动告警]:任务主动告警-解析任务主动告警发生异常!");
            throw new RuntimeException("[任务主动告警]:任务主动告警-解析任务主动告警发生异常!");
        }
        return "<Result value=\"Success\"></Result>";
    }
	
	
	public static void main(String[] args) {
	    String xmlstr="<?xml version='1.0' encoding='UTF-8'?>"
	    		        +"<SecEvent>"
                        +"<CAT1>WEB扫描</CAT1>"
                        +"<CAT2>断网</CAT2>"
                        +"<NAME>断网</NAME>"
	                    +"<SEVERITY>3</SEVERITY>"
                        +"<RULE/>"
                        +"<CT>1</CT>"
                        +"<APP_P/>"
                        +"<TRAN_P/>"
                        +"<URL>http://pangh.com:9080/malware/</URL>"
                        +"<MSG>网络异常</MSG>"
                        +"<TASK_ID>96</TASK_ID>"
                        +"</SecEvent>";
	    
	    String str="[{"
                    +"\"url\": \"http://www.example.com/documentation/06_test_design_considerations.html\","
                    +"\"type\": \"keyword\","
                    +"\"created_at\": \"2011-12-21 19:39:02\","
                    +"\"value\": {"
                        +"\"keywords\": [{"
                            +"\"type\": 1,"
                            +"\"keyword\": \"selenium\","
                            +"\"level\": 3"
                        +"}],"
                    +"}"
                +"}, {"
                    +"\"url\": \"http://www.example.com/documentation/remote-control/02_selenium_ide.html\","
                    +"\"type\": \"keyword\","
                    +"\"created_at\": \"2011-12-21 19:39:02\","
                    +"\"value\": {"
                        +"\"keywords\": [{"
                            +"\"type\": 1,"
                            +"\"keyword\": \"selenium\","
                            +"\"level\": 3"
                        +"}],"
                    +"}"
                +"}]";
	    ClientConfig config = new DefaultClientConfig();
        //检查安全传输协议设置
        Client client = Client.create(config);
        //连接服务器
        WebResource service = client.resource("http://156a1l6950.iask.in/cspi/rest/arnhem/arnhemGetWarn");
        //获取响应结果
//        service.header("Content-Type", "Application/xml");
        String response = service.type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class, xmlstr);
//          String response = service.type(MediaType.APPLICATION_JSON).post(String.class, str);
//        String post = service.accept(MediaType.APPLICATION_XML).post(String.class, xmlstr);
	}
}
