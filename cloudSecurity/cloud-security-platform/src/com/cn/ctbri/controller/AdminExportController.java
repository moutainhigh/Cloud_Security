package com.cn.ctbri.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
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

import sun.misc.BASE64Decoder;

import com.cn.ctbri.constant.WarnType;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.service.IAlarmDDOSService;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.DateUtils;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-2-2
 * 描        述：  告警管理控制层
 * 版        本：  1.0
 */
@Controller
public class AdminExportController {
	
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

    
    private URL base = this.getClass().getResource("");
    
    /**
     * 功能描述：下载导入模板
     * 参数描述：HttpServletRequest request,HttpServletResponse response
     *       @time 2015-1-21
     */
    @RequestMapping(value="/adminexport.html")
    public void export(HttpServletRequest request,HttpServletResponse response) throws Exception{
        try {
            String orderId = request.getParameter("orderId");
            String group_flag = request.getParameter("group_flag");
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
                
                XWPFParagraph paragraph21 = hdt.createParagraph();
                paragraph21.setStyle("2");  
                //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
                XWPFRun run21 = paragraph21.insertNewRun(0);  
                //设置run内容   
                run21.setBold(true); 
                run21.setText("2.1 漏洞分布");
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
                String outfile = orderId+"报告文档-漏洞.docx";
                
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
                    // Opera浏览器只能采用filename*
                    else if (userAgent.indexOf("opera") != -1) {
                        rtn = "filename*=UTF-8''" + new_filename;
                    }
                    // Safari浏览器，只能采用ISO编码的中文输出
                    else if (userAgent.indexOf("safari") != -1) {
                        rtn = "filename=\""
                                + new String(outfile.getBytes("UTF-8"), "ISO8859-1")
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
                  cells.get(0).setColor("ff0000");
              }else if(list.get(0).equals("中")){
                  cells.get(0).setColor("ffc100");
              }if(list.get(0).equals("低")){
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
                      cells.get(0).setColor("ff0000");
                  }else if(list.get(0).equals("中")){
                      cells.get(0).setColor("ffc100");
                  }if(list.get(0).equals("低")){
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
            if(level==0){
                levelName = "低";
            }else if(level==1){
                levelName = "中";
            }else if(level==2){
                levelName = "高";
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
        //查找订单
        Order order = orderService.findOrderById(orderId);
        //获取对应资产
        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
        String webSite = "";
        String webName = "";
        for (Asset asset : assetList) {
            webSite = webSite+asset.getAddr()+","+"\r\n";
            webName = webName+asset.getName()+","+"\r\n";
        }
        webSite = webSite.substring(0, webSite.length()-3);
        webName = webName.substring(0, webName.length()-3);
        
        SimpleDateFormat odf = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
        String createDate = odf.format(new Date());
        
        SimpleDateFormat odf1 = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
        String createDate1 = odf1.format(new Date());
        
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("orderId", orderId);
//        paramMap.put("type", order.getType());
//        paramMap.put("count", assetList.size());
//        paramMap.put("websoc", order.getWebsoc());
        List<Alarm> alarmList = alarmService.getAlarmByOrderId(paramMap);
        
        //低
        paramMap.put("level", WarnType.LOWLEVEL.ordinal());
        List<Alarm> lowList = alarmService.getAlarmByOrderId(paramMap);
        //中
        paramMap.put("level", WarnType.MIDDLELEVEL.ordinal());
        List<Alarm> middleList = alarmService.getAlarmByOrderId(paramMap);
        //高
        paramMap.put("level", WarnType.HIGHLEVEL.ordinal());
        List<Alarm> highList = alarmService.getAlarmByOrderId(paramMap);
        String risk = "";
        if(highList.size()>=2){//高风险
            risk = "高危";
        }else if(highList.size()<=0&&middleList.size()<=0){//低风险
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
        map.put("$LEAKNUM$", String.valueOf(alarmList.size()));
        map.put("$RISK$", risk);
        map.put("$LOWNUM$", String.valueOf(lowList.size()));
        map.put("$MIDDLENUM$", String.valueOf(middleList.size()));
        map.put("$HIGHNUM$", String.valueOf(highList.size()));
        map.put("$webSite$", webSite);
        map.put("$webName$", webName);  
        map.put("$catalog$", "3.2.1 对策");
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
            String[] url = data.split(",");
            String u = url[1];
            // Base64解码
            byte[] b = new BASE64Decoder().decodeBuffer(u);
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
    
}
