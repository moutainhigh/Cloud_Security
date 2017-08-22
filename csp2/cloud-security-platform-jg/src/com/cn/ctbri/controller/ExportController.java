package com.cn.ctbri.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

import org.apache.commons.lang.text.StrBuilder;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.Document;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.cn.ctbri.common.NorthAPIWorker;
import com.cn.ctbri.common.WafAPIAnalysis;
import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.constant.WarnType;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.service.IAlarmDDOSService;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.AttackList;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.FreeMarkerUtils;
import com.cn.ctbri.util.Threat;
import com.cn.ctbri.util.TimeList;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;

import freemarker.template.Configuration;  
import freemarker.template.Template;  
import freemarker.template.TemplateException; 
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-2-2
 * 描        述：  告警管理控制层
 * 版        本：  1.0
 */
@Controller
public class ExportController {
	
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderAssetService orderAssetService;
    @Autowired
    IAlarmService alarmService;
    @Autowired
    ITaskService taskService;
    @Autowired
    ITaskWarnService taskWarnService;
    @Autowired
    IAlarmDDOSService alarmDDOSService;
    @Autowired
    IAssetService assetService;

    
    private URL base = this.getClass().getResource("");
    
    
    @RequestMapping(value="/exportnew.html")
    public void exportnew(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String orderId = request.getParameter("orderId");
    	String iofile = NorthAPIWorker.vulnScanGetReport(orderId);
    }
    /**
     * 功能描述：下载导入模板
     * 参数描述：HttpServletRequest request,HttpServletResponse response
     *       @time 2015-1-21
     */
    @RequestMapping(value="/export.html",method=RequestMethod.POST)
    public void export(HttpServletRequest request,HttpServletResponse response) throws Exception{
        try {
            String orderId = request.getParameter("orderId");
            String group_flag = request.getParameter("group_flag");
            String orderAssetId = request.getParameter("orderAssetId");
            String imgPie = request.getParameter("imgPie");
            String imgBar = request.getParameter("imgBar");
            String imgLine = request.getParameter("imgLine");
            //查找订单
            Order order = orderService.findOrderById(orderId);
            //获取对应资产
            List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderId", orderId);
            paramMap.put("type", order.getType());
            paramMap.put("count", assetList.size());
            paramMap.put("websoc", order.getWebsoc());
            paramMap.put("group_flag", group_flag);
            paramMap.put("orderAssetId", orderAssetId);
            
//            for (Asset asset : assetList) {
                //预备导出数据
                Map<String, String> map = this.getExportData(orderId,paramMap);
                
                String fileDir = new File(base.getFile(), "../../../../../../doc/").getCanonicalPath().replaceAll("%20", " ");
                
                System.out.println(fileDir+"        111111111"+base.getFile());
                //获取模板文件
//                File demoFile=new File(fileDir+"/leak.doc");
                
    //            FileInputStream in = new FileInputStream(demoFile);
    //            HWPFDocument hdt = new HWPFDocument(in);
                
                OPCPackage opcPackage = POIXMLDocument.openPackage(fileDir+"/leak.docx");
                XWPFDocument hdt = new XWPFDocument(opcPackage);
                
                
                //替换读取到的word模板内容的指定字段
    //            Range range = hdt.getRange();
                
                
    //            for (Map.Entry<String,String> entry:map.entrySet()) {
    //                range.replaceText(entry.getKey(),entry.getValue());
    //            }

                // 替换段落中的指定文字  
                Iterator<XWPFParagraph> itPara = hdt.getParagraphsIterator();
                while (itPara.hasNext()) {
                    XWPFParagraph paragraph = (XWPFParagraph) itPara.next();  
                    //String s = paragraph.getParagraphText();          
                    Set<String> set = map.keySet();  
                    Iterator<String> iterator = set.iterator();  
                    while (iterator.hasNext()) {  
                        String key = iterator.next();  
                        List<XWPFRun> run=paragraph.getRuns();
                         for(int i=0;i<run.size();i++)  
                         {  
                          if(run.get(i).getText(run.get(i).getTextPosition())!=null && run.get(i).getText(run.get(i).getTextPosition()).equals(key))  
                          {      
                            /**参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始 
                             * 就可以把原来的文字全部替换掉了 
                            * */           
                              run.get(i).setText(map.get(key),0);
                          }      
                         }      
                    }      
                } 
                
                // 替换表格中的指定文字  
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
                //modify by tangxr 2016-5-17
                /*XWPFParagraph paragraph21 = hdt.createParagraph();
                paragraph21.setStyle("2");  
                //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                XWPFRun run21 = paragraph21.insertNewRun(0);  
                //设置run内容   
                run21.setBold(true); 
                run21.setText("2.1 漏洞分布");*/
                //插入图片
                String userName = System.getProperty("user.name");
                //获取文件路径
                String filePath = request.getSession().getServletContext().getRealPath("/source/chart");
    //            String filePath = "C:\\Users\\"+userName+"\\Desktop\\chart";
                File file = new File(filePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String fileName = filePath +"/"+ System.currentTimeMillis()+".png";
               
                createImage(request, response, fileName, imgPie);
                
    //            XmlCursor cursor = hdt.getDocument().getBody().getPArray(9).newCursor();
                XWPFParagraph p = hdt.createParagraph();
                String blipId = p.getDocument().addPictureData(
                        new FileInputStream(new File(fileName)),
                        Document.PICTURE_TYPE_PNG);
                createPicture(blipId,
                        hdt.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 264, 312, p);
                
                
                XWPFParagraph paragraph22 = hdt.createParagraph();
                paragraph22.setStyle("2");  
                //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                XWPFRun run22 = paragraph22.insertNewRun(0);  
                //设置run内容   
                run22.setBold(true); 
                run22.setText("2.2 漏洞数量");
                
                //插入图片
                String userName1 = System.getProperty("user.name");
                String filePath1 = request.getSession().getServletContext().getRealPath("/source/chart");
                File file1 = new File(filePath1);
                if(!file1.exists()){
                    file1.mkdir();
                }
                String fileName1 = filePath1 +"/"+ System.currentTimeMillis()+".png";
               
                createImage(request, response, fileName1, imgBar);
                
    //            XmlCursor cursor = hdt.getDocument().getBody().getPArray(9).newCursor();
                XWPFParagraph p1 = hdt.createParagraph();
                String blipId1 = p1.getDocument().addPictureData(
                        new FileInputStream(new File(fileName1)),
                        Document.PICTURE_TYPE_PNG);
                createPicture(blipId1,
                        hdt.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 580, 332, p1);
                
                
                
                XWPFParagraph paragraph23 = hdt.createParagraph();
                paragraph23.setStyle("2");  
                //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                XWPFRun run23 = paragraph23.insertNewRun(0);  
                //设置run内容   
                run23.setBold(true); 
                run23.setText("2.3 趋势分析");
                
                if(order.getType()==1){
                    //插入图片
                    String userName2 = System.getProperty("user.name");
                    String filePath2 = request.getSession().getServletContext().getRealPath("/source/chart");
                    File file2 = new File(filePath2);
                    if(!file2.exists()){
                        file2.mkdir();
                    }
                    String fileName2 = filePath2 +"/"+ System.currentTimeMillis()+".png";
                   
                    createImage(request, response, fileName2, imgLine);
                    
    //                XmlCursor cursor = hdt.getDocument().getBody().getPArray(9).newCursor();
                    XWPFParagraph p2 = hdt.createParagraph();
                    String blipId2 = p2.getDocument().addPictureData(
                            new FileInputStream(new File(fileName2)),
                            Document.PICTURE_TYPE_PNG);
                    createPicture(blipId2,
                            hdt.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 580, 332, p2);
                }else{
                    XWPFParagraph para = hdt.createParagraph();
                    //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                    XWPFRun run = para.insertNewRun(0);  
                    //设置run内容   
                    run.setText("无");
                }
                
                
                XWPFParagraph paragraph24 = hdt.createParagraph();
                paragraph24.setStyle("2");  
                //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                XWPFRun run24 = paragraph24.insertNewRun(0);  
                //设置run内容   
                run24.setBold(true); 
                run24.setText("2.4 纵向对比");
                
                XWPFTable newt = hdt.createTable();
                //默认TblW的type属性为STTblWidth.AUTO,即自动伸缩。所以要调整为指定类型：STTblWidth.DXA
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
                ccpr1.setText("漏洞名称");
                
                
                XWPFTableCell c12 =rowr.createCell();
                XWPFParagraph ccp12=c12.getParagraphs().get(0);
                XWPFRun ccpr12 = ccp12.createRun();
                ccpr12.setFontSize(10);
                ccpr12.setBold(true);
                ccpr12.setColor("000000");
                ccpr12.setText("上次发现");
                
                XWPFTableCell c13 =rowr.createCell();
                XWPFParagraph ccp13=c13.getParagraphs().get(0);
                XWPFRun ccpr13 = ccp13.createRun();
                ccpr13.setFontSize(10);
                ccpr13.setBold(true);
                ccpr13.setColor("000000");
                ccpr13.setText("本次发现");
               
                XWPFTableRow two22 = newt.createRow();
                two22.getCell(0).setText("1");
                two22.getCell(1).setText("2");
                two22.getCell(2).setText("3");
                
                //插入表格数据
                insertDataToTable(newt,2,true,1,paramMap);
                
                
                XWPFParagraph paragraph3 = hdt.createParagraph();
                paragraph3.setStyle("1");  
                //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                XWPFRun run3 = paragraph3.insertNewRun(0);  
                //设置run内容   
                run3.setBold(true); 
                run3.setText("3. 检测结果详细报告");
                
                XWPFParagraph paragraph31 = hdt.createParagraph();
                paragraph31.setStyle("2");  
                //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                XWPFRun run31 = paragraph31.insertNewRun(0);  
                //设置run内容   
                run31.setBold(true); 
                run31.setText("3.1 漏洞数量统计");
                
                XWPFTable newtable = hdt.createTable();
                //默认TblW的type属性为STTblWidth.AUTO,即自动伸缩。所以要调整为指定类型：STTblWidth.DXA
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
                ccpr.setText("漏洞类型");
                
                row.createCell().setText("");
                
                XWPFTableCell c1 =row.createCell();
                c1.setColor("285ea1");
                XWPFParagraph ccp1=c1.getParagraphs().get(0);
                XWPFRun ccpr0 = ccp1.createRun();
                ccpr0.setFontSize(10);
                ccpr0.setBold(true);
                ccpr0.setColor("ffffff");
                ccpr0.setText("问题数量");
               
                XWPFTableRow two2 = newtable.createRow();
                two2.getCell(0).setText("1");
                two2.getCell(1).setText("2");
                
                mergeCellsHorizontal(newtable, 0, 0, 1);
                
                //插入表格数据
    //            insertDataToTable(hdt,2,true,1,paramMap);
                insertDataToTable(newtable,2,true,2,paramMap);
                
                
                XWPFParagraph paragraph32 = hdt.createParagraph();
                paragraph32.setStyle("2");  
                //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                XWPFRun run32 = paragraph32.insertNewRun(0);  
                //设置run内容   
                run32.setBold(true); 
                run32.setText("3.2 漏洞详情及加固建议");           
                
                
                List result = alarmService.findAlarmByOrderIdorGroupId(paramMap);
                //目录索引
                int index = 1;
                //加固建议
                String advice ="";
                for (int i = 0; i < result.size(); i++) {
                    XWPFParagraph paragraph = hdt.createParagraph();
                    List<String> list = new ArrayList<String>();
                    Alarm alarm = (Alarm) result.get(i);
                    String name = (String) alarm.getName();
                
                    paragraph.setStyle("3");  
                    //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                    XWPFRun run = paragraph.insertNewRun(0);  
                    //设置run内容   
                    run.setBold(true); 
                    run.setText("3.2."+(index)+" "+name);
                    index++;
                    
                    paramMap.put("name", name);
                    paramMap.put("level", null);
                    List<Alarm> alarmList = alarmService.getAlarmByOrderId(paramMap);
                    for (int j = 0; j < alarmList.size(); j++) {
                        advice = alarmList.get(j).getAdvice();
                        XWPFTable tableOne = hdt.createTable();
                        //默认TblW的type属性为STTblWidth.AUTO,即自动伸缩。所以要调整为指定类型：STTblWidth.DXA
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
                            levelName = "低";
                        }else if(alarmList.get(j).getLevel()==1){
                            levelName = "中";
                        }else if(alarmList.get(j).getLevel()==2){
                            levelName = "高";
                        }else if(alarmList.get(j).getLevel()==-1){
                            levelName = "信息";
                        }else if(alarmList.get(j).getLevel()==3){
                            levelName = "紧急";
                        }
                        XWPFTableCell cell2 = tableOneRowTwo.getCell(0);
                        XWPFParagraph cellP2=cell2.getParagraphs().get(0);
                        XWPFRun cellR2 = cellP2.createRun();
                        cellR2.setFontSize(10);
                        cellR2.setText("严重性："+levelName);
                        
                        XWPFTableRow tableOneRow3 = tableOne.createRow();
                        XWPFTableCell cell3 = tableOneRow3.getCell(0);
                        XWPFParagraph cellP3=cell3.getParagraphs().get(0);
                        XWPFRun cellR3 = cellP3.createRun();
                        cellR3.setFontSize(10);
                        cellR3.setText("URL："+alarmList.get(j).getAlarm_content());
                        
                        XWPFTableRow tableOneRow4 = tableOne.createRow();
                        XWPFTableCell cell4 = tableOneRow4.getCell(0);
                        XWPFParagraph cellP4=cell4.getParagraphs().get(0); 
                        XWPFRun cellR4 = cellP4.createRun();
                        cellR4.setFontSize(10); 
                        cellR4.setText("弱点内容：");
                        cellR4.addBreak();
                        
                        XWPFRun r = cellP4.createRun();
                        r.setFontSize(10);
                        r.setColor("285ea1");
                        r.setText(alarmList.get(j).getKeyword());
                        r.addBreak();
                        
                        hdt.createParagraph();
                    }
                    //加固建议
                    XWPFParagraph detailsp = hdt.createParagraph();
                    detailsp.setStyle("3");
                    //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                    XWPFRun rundetails = detailsp.insertNewRun(0);  
                    //设置run内容   
                    rundetails.setBold(true); 
                    rundetails.setText("3.2."+(index)+" "+name+"加固建议"); 
                    index++;
                    
                    XWPFTable tabledetails = hdt.createTable();
                    //默认TblW的type属性为STTblWidth.AUTO,即自动伸缩。所以要调整为指定类型：STTblWidth.DXA
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

                
                //输出word内容文件流，提供下载
                response.reset();
                response.setContentType("application/x-msdownload");
                String outfile = orderId+ "_" + webName +"_报告文档-漏洞.docx";
                
                String userAgent = request.getHeader("User-Agent");
                String new_filename = URLEncoder.encode(outfile, "UTF8");
                // 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
                String rtn = "filename=\"" + new_filename + "\"";
                if (userAgent != null) {
                    userAgent = userAgent.toLowerCase();
                    // IE浏览器，只能采用URLEncoder编码
                    if (userAgent.indexOf("msie") != -1) {
                        rtn = "filename=\"" + new_filename + "\"";
                    }
                    else if (userAgent.indexOf("edge") != -1) {
                    	rtn = "filename=\"" + new_filename + "\"";
                    }
                    // Opera浏览器只能采用filename*
                    else if (userAgent.indexOf("opera") != -1) {
                        rtn = "filename*=UTF-8''" + new_filename;
                    }
                    // Safari浏览器，只能采用ISO编码的中文输出
                    else if (userAgent.indexOf("safari") != -1) {
                        rtn = "filename=\""
                                //+ new String(outfile.getBytes("UTF-8"), "ISO8859-1")
                        		+ outfile
                                + "\"";
                    }
                    // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                    else if (userAgent.indexOf("applewebkit") != -1) {
                        new_filename = MimeUtility.encodeText(outfile, "UTF8", "B");
                        rtn = "filename=\"" + new_filename + "\"";
                    }
                    // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                    else if (userAgent.indexOf("mozilla") != -1) {
                        rtn = "filename*=UTF-8''" + new_filename;
                    }
                } 
                response.addHeader("Content-Disposition", "attachment;"+rtn);
//                response.setHeader("Content-Disposition", "attachment;filename=" + new String( outfile.getBytes("utf-8"), "ISO8859-1" ) );
//                response.addHeader("Content-Disposition", "attachment; filename=\"1.docx\"");
                ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                ServletOutputStream servletOS = response.getOutputStream();
                hdt.write(ostream);
                servletOS.write(ostream.toByteArray());
                servletOS.flush();
                servletOS.close();
                
//            }
//            String FilePath = "D:\\"; 
//            String tmpFileName = order.getId()+"漏洞报告.zip";    
//            byte[] buffer = new byte[1024];    
//            String strZipPath = FilePath + tmpFileName;    
//            try {    
//                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(    
//                        strZipPath));    
//                // 需要同时下载的两个文件result.txt ，source.txt    
//                File[] file1 = { new File(FilePath+"test1.txt"),    
//                        new File(FilePath+"test2.txt") };    
//                for (int i = 0; i < file1.length; i++) {    
//                    FileInputStream fis = new FileInputStream(file1[i]);    
//                    out.putNextEntry(new ZipEntry(file1[i].getName()));    
//                    //设置压缩文件内的字符编码，不然会变成乱码    
//                    out.setEncoding("GBK");    
//                    int len;    
//                    // 读入需要下载的文件的内容，打包到zip文件    
//                    while ((len = fis.read(buffer)) > 0) {    
//                        out.write(buffer, 0, len);    
//                    }    
//                    out.closeEntry();    
//                    fis.close();    
//                }    
//                out.close();    
//                this.downFile(response, tmpFileName);    
//            } catch (Exception e) {   
//                e.printStackTrace();
////                Log.error("文件下载出错", e);    
//            }    
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            
        }
    }
    
    /**   
     * 文件下载   
     * @param response   
     * @param str   
     */    
    private void downFile(HttpServletResponse response, String str) {    
        try {    
            String FilePath = "D:\\"; 
            String path = FilePath + str;    
            File file = new File(path);    
            if (file.exists()) {    
                InputStream ins = new FileInputStream(path);    
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面    
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流    
                BufferedOutputStream bouts = new BufferedOutputStream(outs);    
                response.setContentType("application/x-download");// 设置response内容的类型    
                response.setHeader(    
                        "Content-disposition",    
                        "attachment;filename="    
                                + URLEncoder.encode(str, "UTF-8"));// 设置头部信息    
                int bytesRead = 0;    
                byte[] buffer = new byte[8192];    
                // 开始向网络传输文件流    
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {    
                    bouts.write(buffer, 0, bytesRead);    
                }    
                bouts.flush();// 这里一定要调用flush()方法    
                ins.close();    
                bins.close();    
                outs.close();    
                bouts.close();    
            } else {    
                response.sendRedirect("../error.jsp");    
            }    
        } catch (IOException e) {    
            e.printStackTrace();
//            Log.error("文件下载出错", e);    
        }    
    }    

    private void insertDataToTable(XWPFTable newtable,int tableSize,boolean isDelTmpRow,int tableIndex, Map<String, Object> paramMap) throws Exception {
        List<List<String>> resultList = generateData(tableIndex,paramMap);
        insertValueToNewTable(newtable,resultList,tableSize,isDelTmpRow,tableIndex);
        
    }
    
    
    /**
     * @Description: 按模版行样式填充数据,暂未实现特殊样式填充(如列合并)，只能用于普通样式(如段落间距 缩进 字体 对齐)
     * @param resultList 填充数据
     * @param tableRowSize 模版表格行数 取第一个行数相等列数相等的表格填充
     * @param isDelTmpRow 是否删除模版行
     */
    //TODO 数据行插到模版行下面，没有实现指定位置插入
    public void insertValueToTable(XWPFDocument doc,
        List<List<String>> resultList,int tableRowSize,boolean isDelTmpRow,int tableIndex) throws Exception {
//      Iterator<XWPFTable> iterator = doc.getTablesIterator();
      XWPFTable table = doc.getTableArray(tableIndex);
//      XWPFTable table = null;
      List<XWPFTableRow> rows=null;
      List<XWPFTableCell> cells=null;
      List<XWPFTableCell> tmpCells=null;//模版列
      XWPFTableRow tmpRow=null;//匹配用
      XWPFTableCell tmpCell=null;//匹配用
      boolean flag=false;//是否找到表格
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
        //插入的行会填充与表格第一行相同的列数
        for(int k=0,klen=cells.size();k<klen;k++){
          if(tableIndex==2){
              XWPFTableCell cell = null;
              cell=cells.get(k);
              if(list.get(0).equals("高")){
                  cells.get(0).setColor("ff7e50");
              }else if(list.get(0).equals("中")){
                  cells.get(0).setColor("ffa500");
              }else if(list.get(0).equals("低")){
                  cells.get(0).setColor("1e91ff");
              }else if(list.get(0).equals("信息")){
                  cells.get(0).setColor("40e1d1");
              }else if(list.get(0).equals("紧急")){
                  cells.get(0).setColor("cd5c5c");
              }
              tmpCell=tmpCells.get(k);
              setCellText(tmpCell, cell, list.get(k));
          }else{
              tmpCell=tmpCells.get(k);
              XWPFTableCell cell=cells.get(k);
              setCellText(tmpCell, cell, list.get(k));
          }
          
        }
        //继续写剩余的列
        for(int j=cells.size(),jlen=list.size();j<jlen;j++){
          tmpCell=tmpCells.get(j);
          XWPFTableCell cell=row.addNewTableCell();
            setCellText(tmpCell, cell, list.get(j));
        }
      }
      //删除模版行
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
    
    
  //TODO 数据行插到模版行下面，没有实现指定位置插入
    public void insertValueToNewTable(XWPFTable newtable,
            List<List<String>> resultList,int tableRowSize,boolean isDelTmpRow,int tableIndex) throws Exception {
//          Iterator<XWPFTable> iterator = doc.getTablesIterator();
          XWPFTable table = newtable;
//          XWPFTable table = null;
          List<XWPFTableRow> rows=null;
          List<XWPFTableCell> cells=null;
          List<XWPFTableCell> tmpCells=null;//模版列
          XWPFTableRow tmpRow=null;//匹配用
          XWPFTableCell tmpCell=null;//匹配用
          boolean flag=false;//是否找到表格
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
            //插入的行会填充与表格第一行相同的列数
            for(int k=0,klen=cells.size();k<klen;k++){
              if(tableIndex==2){
                  XWPFTableCell cell = null;
                  cell=cells.get(k);
                  if(list.get(0).equals("高")){
                      cells.get(0).setColor("ff7e50");
                  }else if(list.get(0).equals("中")){
                      cells.get(0).setColor("ffa500");
                  }else if(list.get(0).equals("低")){
                      cells.get(0).setColor("1e91ff");
                  }else if(list.get(0).equals("信息")){
                      cells.get(0).setColor("40e1d1");
                  }else if(list.get(0).equals("紧急")){
                      cells.get(0).setColor("cd5c5c");
                  }
                  tmpCell=tmpCells.get(k);
                  setCellText(tmpCell, cell, list.get(k));
              }else{
                  tmpCell=tmpCells.get(k);
                  XWPFTableCell cell=cells.get(k);
                  setCellText(tmpCell, cell, list.get(k));
              }
              
            }
            //继续写剩余的列
            for(int j=cells.size(),jlen=list.size();j<jlen;j++){
              tmpCell=tmpCells.get(j);
              XWPFTableCell cell=row.addNewTableCell();
                setCellText(tmpCell, cell, list.get(j));
            }
          }
          //删除模版行
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
      //复制字体信息
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
      //复制段落信息
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
          //复制段落间距信息
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
          //复制段落缩进信息
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

    
    //生成数据
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
            if(level==-1){
            	levelName = "信息";
            }else if(level==0){
                levelName = "低";
            }else if(level==1){
                levelName = "中";
            }else if(level==2){
                levelName = "高";
            }else if(level==3){
            	levelName = "紧急";
            }
            list.add(levelName);
            list.add(name);
            list.add(String.valueOf(num));
        }
        resultList.add(list);
      }
      return resultList;
    }

    String webName = "";
    private Map<String, String> getExportData(String orderId, Map<String, Object> paramMap) {
        //查找订单
        Order order = orderService.findOrderById(orderId);
        //获取对应资产
        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
        int orderAssetId = Integer.parseInt(paramMap.get("orderAssetId").toString());
//        Asset asset = assetService.findByOrderAssetId(orderAssetId);
//        String webSite = asset.getAddr();
//        webName = asset.getName();
        OrderAsset oa = orderAssetService.findOrderAssetById(orderAssetId);
        String webSite = oa.getAssetAddr();
        webName = oa.getAssetName();
        
        SimpleDateFormat odf = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
        String createDate = odf.format(new Date());
        
        SimpleDateFormat odf1 = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
        String createDate1 = odf1.format(new Date());
        
        //add by tangxr 2016-5-17
        int message = 0;
        int higher = 0;
        int high = 0;
        int middle = 0;
        int low = 0;
        int count = 0;
        String ratioHigher = "";
        String ratioHigh = "";
        String ratioMiddle = "";
        String ratioLow = "";
        String ratioMessage = "";
        List result = alarmService.getAlarmByParam(paramMap);
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        if(result != null && result.size() > 0){
        	for(int i = 0; i < result.size(); i++){
        		HashMap<String,Object>  map = (HashMap<String,Object>)result.get(i);
        		if((Integer)map.get("level")==3){
        			higher = Integer.parseInt(map.get("count").toString());
        			ratioHigher = df.format((float)higher/count*100)+"%";
        		}
        		if((Integer)map.get("level")==2){
        			high = Integer.parseInt(map.get("count").toString());
        			ratioHigh = df.format((float)high/count*100)+"%";
        		}
        		if((Integer)map.get("level")==1){
        			middle = Integer.parseInt(map.get("count").toString());
        			ratioMiddle = df.format((float)middle/count*100)+"%";
        		}
        		if((Integer)map.get("level")==0){
        			low = Integer.parseInt(map.get("count").toString());
        			ratioLow = df.format((float)low/count*100)+"%";
        		}
        		if((Integer)map.get("level")==-1){
	    			message = Integer.parseInt(map.get("count").toString());
	    		}
        	}
        }
        count = higher + high + middle + low + message;
        String remark = "共发现漏洞"+ count +"个，其中，";
        if(higher>0){
        	ratioHigher = df.format((float)higher/count*100)+"%";
        	remark = remark + "紧急漏洞"+ higher +"个，占比"+ ratioHigher +"；";
        }
        if(high>0){
        	ratioHigh = df.format((float)high/count*100)+"%";
        	remark = remark + "高危漏洞"+ high +"个，占比"+ ratioHigh +"；";
        }
        if(middle>0){
        	ratioMiddle = df.format((float)middle/count*100)+"%";
        	remark = remark + "中危漏洞"+ middle +"个，占比"+ ratioMiddle +"；";
        }
        if(low>0){
        	ratioLow = df.format((float)low/count*100)+"%";
        	remark = remark + "低危漏洞"+ low +"个，占比"+ ratioLow +"；";
        }
        
        if(message>0){
        	ratioMessage = df.format((float)message/count*100)+"%";
        	remark = remark + "信息类漏洞"+ message +"个，占比"+ ratioMessage +"；";
        }
        //替换结尾句号
        if(remark.endsWith("；")){
        	remark = remark.substring(0, remark.lastIndexOf("；"));
        	remark = remark + "。";
        }
        
//        List<Alarm> alarmList = alarmService.getAlarmByOrderId(paramMap);
//        
//        //低
//        paramMap.put("level", WarnType.LOWLEVEL.ordinal());
//        List<Alarm> lowList = alarmService.getAlarmByOrderId(paramMap);
//        //中
//        paramMap.put("level", WarnType.MIDDLELEVEL.ordinal());
//        List<Alarm> middleList = alarmService.getAlarmByOrderId(paramMap);
//        //高
//        paramMap.put("level", WarnType.HIGHLEVEL.ordinal());
//        List<Alarm> highList = alarmService.getAlarmByOrderId(paramMap);
        String risk = "";
        if(higher+high>=2){//高风险
            risk = "高危";
        }else if(higher+high<=0 && middle<=0){//低风险
            risk = "低危";
        }else{//中风险
            risk = "中危";
        }
        
        Map<String, String> map = new HashMap<String, String>();
        
        if(order.getTask_date()!=null){
            order.setTask_datevo(( DateUtils.dateToString(order.getTask_date())));
        }
        map.put("$createDate$", createDate);
        map.put("$createDate1$", createDate1);
        map.put("$JCSJ$", order.getTask_datevo());
        map.put("$LEAKNUM$", String.valueOf(count));
        map.put("$RISK$", risk);
        map.put("$MESSAGE$", String.valueOf(message));
        map.put("$LOWNUM$", String.valueOf(low));
        map.put("$MIDDLENUM$", String.valueOf(middle));
        map.put("$HIGHNUM$", String.valueOf(high));
        map.put("$HIGHERNUM$", String.valueOf(higher));
        map.put("$webSite$", webSite);
        map.put("$webName$", webName);  
        map.put("$catalog$", "3.2.1 对策");
        
        map.put("$REMARKS$", remark);
        return map;

    }
    

    /** 
     * 将输入流中的数据写入字节数组 
     * @param in 
     * @return 
     */  
    public static byte[] inputStream2ByteArray(InputStream in,boolean isClose){  
        byte[] byteArray = null;  
        try {  
            int total = in.available();  
            byteArray = new byte[total];  
            in.read(byteArray);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if(isClose){  
                try {  
                    in.close();  
                } catch (Exception e2) {  
                    System.out.println("关闭流失败");  
                }  
            }  
        }  
        return byteArray;  
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
        	String replaceData = data.replaceAll(" ", "+");
            String[] url = replaceData.split(",");
            System.out.println("file png raw data *******************: "+data);
            String u = url[1];
            // Base64解码
            byte[]  b= new BASE64Decoder().decodeBuffer(u);
          //  System.out.println("file png *******************: "+ Arrays.toString(b));
            // 生成图片
            OutputStream out = new FileOutputStream(new File(fileName));
            out.write(b);
            out.flush();
            out.close();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Description: 跨列合并
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
     * @Description: 得到Cell的CTTcPr,不存在则新建
     */
    public CTTcPr getCellCTTcPr(XWPFTableCell cell) {
      CTTc cttc = cell.getCTTc();
      CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
      return tcPr;
    }
    
    public static String getFromBASE64(String s) { 
    	if (s == null) return null; 
    	BASE64Decoder decoder = new BASE64Decoder(); 
    	try { 
    	byte[] b = decoder.decodeBuffer(s); 
    	return new String(b,"utf-8"); 
    	} catch (Exception e) { 
    	return null; 
    	} 
    }

    /** 
     * @Title:hexString2String 
     * @Description:16进制字符串转字符串 
     * @param src 
     *            16进制字符串 
     * @return 字节数组 
     * @throws 
     */  
    /*public static String hexString2String(String src) {  
    	src = src.substring(2);
        String temp = "";  
        for (int i = 0; i < src.length() / 2; i++) {  
            temp = temp  
                    + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),  
                            16).byteValue();  
        }  
        return temp;  
    } */
    public static String hexString2String(String s) {  
        if (s == null || s.equals("")) {  
            return null;  
        }  
        s = s.replace(" ", ""); 
        
        
        byte[] baKeyword = new byte[s.length() / 2];  
        for (int i = 0; i < baKeyword.length; i++) {  
            try {  
                baKeyword[i] = (byte) (0xff & Integer.parseInt(  
                        s.substring(i * 2, i * 2 + 2), 16));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        try {  
            s = new String(baKeyword, "UTF-8");  
            new String();  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
        return s;  
    }  
    /**
     * 功能描述：在初始时间上加减时间
     * 参数描述：String orignalTimestr 初始时间,int addnum 间隔时间 ,String type 时间类型 1、月 2、年  3、周
     *       @time 2017-6-29
     */
    
    public String TimeCalc(String orignalTimestr,int addnum,String type)
    {
    	SimpleDateFormat sdf;
    	String reTimeString ="";
    	if (type!=null&&type.equals("月报")) {
    		sdf=new SimpleDateFormat("yyyy-MM");
		}else if (type!=null&&type.equals("年报")) {
			sdf=new SimpleDateFormat("yyyy");
		}else  {
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}
    	try {
			Date dt = sdf.parse(orignalTimestr);
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(dt);
			//beginCalendar.add(field, amount);
			if (type!=null&&type.equals("月报")) {
				endCalendar.add(Calendar.MONTH, addnum);
			}else if (type!=null&&type.equals("年报")) {
				endCalendar.add(Calendar.YEAR, addnum);
			}else if (type!=null&&type.equals("周报")){
				endCalendar.add(Calendar.DAY_OF_YEAR, addnum*7);
			}
			
			Date endDate = endCalendar.getTime(); 
			reTimeString = sdf.format(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    	return reTimeString;
    }
    /**
     * 功能描述：下载waf导入模板
     * 参数描述：HttpServletRequest request,HttpServletResponse response
     *       @time 2017-6-29
     */
    @RequestMapping(value="/exportWAF.html",method=RequestMethod.POST)
    public void exportWAF(HttpServletRequest request,HttpServletResponse response) throws Exception{
    		//System.out.println("");
            String orderId = request.getParameter("orderId");
            String group_flag = request.getParameter("group_flag");
            String orderAssetId = request.getParameter("orderAssetId");
            String imgPieLevelHex = request.getParameter("imgPieLevel");
            String imgBarHex = request.getParameter("imgBar");
            String imgPieEventHex = request.getParameter("imgPieEvent");
            String imgSourceIpHex = request.getParameter("imgSourceIp");
            String imgOntimeLineHex = request.getParameter("imgOntimeLine");
            String ipurl = request.getParameter("ipurl"); 
            String defenselength = request.getParameter("defenselength");
            
            
          //获取报表类型  月、年
    	    String reporttype = request.getParameter("radioType");
    	    if (reporttype.equals("month")) {
    	    	reporttype = "月报";
    		}else if (reporttype.equals("year")) {
    			reporttype = "年报";
    		}else if (reporttype.equals("week")) {
    			reporttype = "周报";
			}else{
				reporttype = " ";
			}
    	    String beginDate = request.getParameter("beginDate");
            String endDate = TimeCalc(beginDate, 1, reporttype);
            //16进制转base64数据
            
            String imgPieLevel = hexString2String(imgPieLevelHex);
            String imgBar = hexString2String(imgBarHex);
            String imgPieEvent = hexString2String(imgPieEventHex);
            String imgSourceIp = hexString2String(imgSourceIpHex);
            String imgOntimeLine = hexString2String(imgOntimeLineHex);
            
            String levelTotal = request.getParameter("level");
            String levelhigh = request.getParameter("levelhigh");
            String levelmid = request.getParameter("levelmid");
            String levellow = request.getParameter("levellow");
            String listtimeString = request.getParameter("resultList");
            
            String timeCountTotal = request.getParameter("timeCountTotal"); // time
          //  String timeStrBase64 = request.getParameter("resultListTime");
          //  timeStrBase64 = timeStrBase64.replaceAll(" ", "+");
            String timeStrHex = request.getParameter("resultListTime");
            String timeJsonStr =hexString2String(timeStrHex);
            
           // String strattackipBase64 = request.getParameter("websecListIp");   //ip
            String totalAttackIPStr = request.getParameter("totalAttackIP");
            //strattackipBase64 = strattackipBase64.replaceAll(" ", "+");
            String strattackipHex = request.getParameter("websecListIp");
            String strJsonattackip = hexString2String(strattackipHex);
            
            String eventTypeTotalstr = request.getParameter("eventTypeTotal");  //event type
           // String strEventTypeBase64 = request.getParameter("strlistEventType");
            //strEventTypeBase64 = strEventTypeBase64.replaceAll(" ", "+"); // 浏览器出现 将base64中的＋转换为空格
            String eventTypeHex = request.getParameter("strlistEventType");
            String strJsonEventTypeStr = hexString2String(eventTypeHex);
            
            imgPieLevel = imgPieLevel.replaceAll(" ", "+");
            imgBar = imgBar.replaceAll(" ", "+");
            imgPieLevel = imgPieLevel.replaceAll(" ", "+");
            imgSourceIp = imgSourceIp.replaceAll(" ", "+");
            imgOntimeLine = imgOntimeLine.replaceAll(" ", "+");
            
            System.out.println("imgPie"+imgPieLevel);
            System.out.println("imgbar:"+imgBar);
            System.out.println("imgpieevent"+imgPieEvent);
            //查找订单
            Order order = orderService.findOrderById(orderId);
            //获取对应资产
            List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("ipurl", ipurl);
            paramMap.put("defenselength", defenselength);
            paramMap.put("reporttype", reporttype);
            paramMap.put("orderId", orderId);
            paramMap.put("type", order.getType());
            paramMap.put("count", assetList.size());
            paramMap.put("websoc", order.getWebsoc());
            paramMap.put("group_flag", group_flag);
            paramMap.put("orderAssetId", orderAssetId);
            paramMap.put("imgPieLevel", imgPieLevel);
            paramMap.put("imgBar", imgBar);
            paramMap.put("imgPieEvent", imgPieEvent);
            paramMap.put("imgSourceIp", imgSourceIp);
            paramMap.put("imgOntimeLine", imgOntimeLine);
            paramMap.put("levelTotal", levelTotal);
            paramMap.put("timeJsonStr", timeJsonStr);//time
            paramMap.put("timeCountTotal", timeCountTotal);
            paramMap.put("strJsonattackip", strJsonattackip);//ip
            paramMap.put("totalAttackIPStr", totalAttackIPStr);
            paramMap.put("eventTypeTotalstr", eventTypeTotalstr);//event
            paramMap.put("strJsonEventTypeStr", strJsonEventTypeStr);
            paramMap.put("levelhigh", levelhigh);
            paramMap.put("levelmid", levelmid);
            paramMap.put("levellow", levellow);
            paramMap.put("beginDate", beginDate);
            paramMap.put("endDate", endDate);
            
            
            
            
            
            
//            for (Asset asset : assetList) {
                //预备导出数据
                Map<String, Object> dataMap = this.getExportWafData(orderId,paramMap,request,response);
                response.setCharacterEncoding("UTF-8");  
                response.setContentType("application/msword");
                File file = null;
                InputStream inputStream = null;
                ServletOutputStream out = null;
                try {

                    String fileName = orderId+"wafinfo.doc";
                    fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
                    response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

                    file = FreeMarkerUtils.write("waffreemarker.ftl", dataMap);
                    inputStream = new FileInputStream(file);
                    out = response.getOutputStream();

                    byte[] buffer = new byte[512];  // 缓冲区  
                    int bytesToRead = -1;  
                    // 通过循环将读入的Word文件的内容输出到浏览器中  
                    while((bytesToRead = inputStream.read(buffer)) != -1) {  
                        out.write(buffer, 0, bytesToRead);  
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    try {
                        if (inputStream != null) {
                                inputStream.close();
                        }
                        if (out != null) {
                            out.close();
                        }

                        if (file != null) {
                            file.delete();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
    }
    private String getImageStr(String fileName) throws Exception{
    	 
    	                
    	//String imgFileString = "";
    	InputStream in = null;
    	byte[]  data = null;
    	in = new FileInputStream(fileName);
    	data = new byte[in.available()];
    	in.read(data);
    	in.close();
    	BASE64Encoder encoder = new BASE64Encoder();
    	
		return encoder.encode(data);
	}
    private Map<String, Object> getExportWafData(String orderId, Map<String, Object> paramMap,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    
    	//查找订单
        Order order = orderService.findOrderById(orderId);
        //获取对应资产
        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
        Object oAssetId = paramMap.get("orderAssetId");
        //查询时间
       // String startDate = request.getParameter("beginDate");
        //周期类型
        //String timeUnit = request.getParameter("type");
  
        int orderAssetId=0;
        if(oAssetId!=null)
        {
        	orderAssetId = Integer.parseInt(paramMap.get("orderAssetId").toString());
        }
        
        OrderAsset oa = orderAssetService.findOrderAssetById(orderAssetId);
        String webSite = oa.getAssetAddr();
        String wafName = "";
        wafName = oa.getAssetName();
        SimpleDateFormat odf = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
        String createDate = odf.format(new Date());
        SimpleDateFormat odf1 = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
        String createDate1 = odf1.format(new Date());
        
        String stripurl = paramMap.get("ipurl").toString();
        String strdefenselength = paramMap.get("defenselength").toString();
 
        
        String strimgPieLevel = paramMap.get("imgPieLevel").toString();
        String strimgBar = paramMap.get("imgBar").toString();
        String strimgPieEvent = paramMap.get("imgPieEvent").toString();
        String strimgSourceIp = paramMap.get("imgSourceIp").toString();
        String strimgOntimeLine = paramMap.get("imgOntimeLine").toString();
        
        String levelTotal =paramMap.get("levelTotal").toString();
        String levelhigh =paramMap.get("levelhigh").toString();
        String levelmid =paramMap.get("levelmid").toString();
        String levellow =paramMap.get("levellow").toString();
        
        String timeCountTotal = paramMap.get("timeCountTotal").toString();
        String eventTypeTotalstr = paramMap.get("eventTypeTotalstr").toString();
        String strreporttype = paramMap.get("reporttype").toString();
        
        String beginDate = paramMap.get("beginDate").toString();
        String endDate = paramMap.get("endDate").toString();
        
        String strJsonEventTypeStr = paramMap.get("strJsonEventTypeStr").toString();  // eventType
        List listEventType = WafAPIAnalysis.analysisEventTypeCountList(strJsonEventTypeStr);
       /* eventListStr = eventListStr.substring(1,eventListStr.length()-1);
        eventListStr = eventListStr.replaceAll("},", "}:");
        System.out.println("eventListStr============"+eventListStr);
        List eventListArray = Arrays.asList(eventListStr.split(":"));*/
        
        String timeJsonStr = paramMap.get("timeJsonStr").toString();   //time
        List listTime = WafAPIAnalysis.analysisWafLogWebSecTimeCountList(timeJsonStr);
       /* timeListStr = timeListStr.substring(1,timeListStr.length()-1); // 去掉头和尾的 ［］
        timeListStr =timeListStr.replaceAll("},", "}:");
        System.out.println("timeListStr================"+timeListStr);
        List  timeListArray = Arrays.asList(timeListStr.split(":")); */
        
        String strJsonattackip = paramMap.get("strJsonattackip").toString(); //ip
        List listattackIP = WafAPIAnalysis.getWafLogWebsecSrcIp(strJsonattackip);
        /*
        websecListStr = websecListStr.substring(1,websecListStr.length()-1);
        if (websecListStr.indexOf("},")!=-1) {
        	websecListStr = websecListStr.replaceAll("},", "}:");
		}
        System.out.println("webseclistStr============"+websecListStr);
        List  websecListArray = Arrays.asList(websecListStr.split(":")); */
        
        String filePath1 = request.getSession().getServletContext().getRealPath("/source/chart");
        File file1 = new File(filePath1);
        if(!file1.exists()){
            file1.mkdir();
        }
        String imgFilePieLevel = filePath1 +"/"+ System.currentTimeMillis()+"strimgPieLevel"+".png";
        String imgFileBar = filePath1 +"/"+ System.currentTimeMillis()+"strimgBar"+".png";
        String imgFilePieEvent = filePath1 +"/"+ System.currentTimeMillis()+"strimgPieEvent"+".png";
        String imgFileSourceIp =filePath1 +"/"+ System.currentTimeMillis() +"strimgSourceIp"+".png";
        String imgFileOntimeLine = filePath1 +"/"+ System.currentTimeMillis() +"strimgOntimeLine"+".png"; 
        
        createImage(request, response, imgFilePieLevel, strimgPieLevel);
        createImage(request, response, imgFileBar, strimgBar);
        createImage(request, response, imgFilePieEvent, strimgPieEvent);
        createImage(request, response, imgFileSourceIp, strimgSourceIp);
        createImage(request, response, imgFileOntimeLine, strimgOntimeLine);

        //高中低 数据统计
        //*****************test WafAPIWorker
       // String levelStr = WafAPIWorker.getWafAlertLevelCountInTime(startDate,"",timeUnit,dstIpList);
    	//Map mapLevelcount = WafAPIAnalysis.getWafAlertLevelCount(levelStr);
    	//Integer totallevel = (Integer) mapLevelcount.get("total");
    	//*****************
        
        Map<String, Object> map = new HashMap<String, Object>();
     //   String imgagefilePath = request.getSession().getServletContext().getRealPath("/source/chart");
     //   String fileName = imgagefilePath +"/"+ "1498719516115"+".png";
        try {
        	
            map.put("createDate1", createDate1);
            map.put("webName", wafName);
            map.put("webSite",webSite);
            map.put("JCSJ", order.getBegin_date().toString());
            map.put("LEAKNUM", levelTotal);
            map.put("HIGHNUM", levelhigh);
            map.put("MIDDLENUM", levelmid);
            map.put("LOWNUM", levellow);
            map.put("threattotal", eventTypeTotalstr);
            map.put("timetotal", timeCountTotal);
			map.put("REPORTTYPE", strreporttype);
			map.put("IP", stripurl);
			map.put("DEFENDLENGTH", strdefenselength);
			map.put("STARTTIME", beginDate);
			map.put("ENDTIME", endDate);
			
			List<Threat> threatlist = new ArrayList<Threat>();  
	        /*for (int i = 0; i < eventListArray.size(); i++) {  
	        	Threat t = new Threat();
	        	String eventTypeStr = (String)eventListArray.get(i);
	        	String[] spilt = eventTypeStr.split(",");
	        	String count = spilt[0].substring(spilt[0].indexOf("{count=")+7);
	        	String eventTypeString = spilt[1].substring(spilt[1].indexOf("eventType=")+10,spilt[1].length()-1);
	            t.setNum(count);  
	            t.setName(eventTypeString);  
	            threatlist.add(t);  
	        }   */
			
			for (int i = 0; i < listEventType.size(); i++) {
				Threat t = new Threat();
				Map typeMap = (Map) listEventType.get(i);
				String count = String.valueOf(typeMap.get("count"));
				String eventTypeString = String.valueOf(typeMap.get("eventType"));
				t.setNum(count);  
	            t.setName(eventTypeString);  
	            threatlist.add(t); 
			}
	        map.put("threatList", threatlist);  
	        
	        List<TimeList> tl = new ArrayList<TimeList>();  
	        /*for (int i = 0; i < timeListArray.size(); i++) {  
	        	TimeList t = new TimeList();  
	        	String strTime = (String) timeListArray.get(i);
	        	String[] spilt =  strTime.split(",");
	        	String count = spilt[0].substring(spilt[0].indexOf("{count=")+7);
	        	String timeName = spilt[1].substring(spilt[1].indexOf("time=")+5,spilt[1].length()-1);
	            t.setNum(count);
	            t.setName(timeName);  
	            timeList.add(t);  
	        }*/
	        for (int i = 0; i < listTime.size(); i++) {
				TimeList t = new TimeList();
				Map timeMap = (Map)listTime.get(i);
				String count = String.valueOf(timeMap.get("count"));
				String timeName = String.valueOf(timeMap.get("time"));
				t.setName(timeName);
				t.setNum(count);
				tl.add(t);
			}
	        
	        map.put("timeList",tl);
	        
	        List<AttackList> attackList = new ArrayList<AttackList>();  
	        /*for (int i = 0; i < websecListArray.size(); i++) {  
	        	AttackList t = new AttackList();  
	        	String strAttack = String.valueOf(websecListArray.get(i));
	        	String[] spilt =  strAttack.split(",");
	        	String count = spilt[1].substring(spilt[1].indexOf("count=")+6,spilt[1].length()-1);
	        	String attackIP = spilt[0].substring(spilt[0].indexOf("{srcIp=")+7);
	            t.setNum(count);;  
	            t.setSourceIP(attackIP);  
	            attackList.add(t);  
	        } */
	        for (int i = 0; i < listattackIP.size(); i++) {
				AttackList t = new AttackList();
				Map ipMap = (Map)listattackIP.get(i);
				String count = String.valueOf(ipMap.get("count"));
				String strsrcIP = String.valueOf(ipMap.get("srcIp"));
				String strCountry = String.valueOf(ipMap.get("country"));
				String strSubdiv = String.valueOf(ipMap.get("subdiv"));
				t.setNum(count);
				t.setSourceIP(strsrcIP);
				t.setCountry(strCountry);
				t.setSubdiv(strSubdiv);
				attackList.add(t);
			}
	        map.put("attackList", attackList);
	        
	        map.put("img6", getImageStr(imgFilePieLevel));
			map.put("img5", getImageStr(imgFileBar));
			map.put("img4", getImageStr(imgFilePieEvent));
			map.put("img3", getImageStr(imgFileOntimeLine));
			map.put("img2", getImageStr(imgFileSourceIp));
			//map.put("img1", getImageStr(imgFilePieEvent));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return map;
    }
    
}
