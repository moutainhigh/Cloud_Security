package com.cn.ctbri.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
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

import com.cn.ctbri.common.WafAPIAnalysis;
import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.service.IAlarmDDOSService;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.ExportUtils;
/**
 * ??? ??? ???  ???  tang
 * ???????????????  2017-7-5
 * ???        ??????  waf????????????
 * ???        ??????  1.0
 */
@Controller
public class ExportWafController {
	
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
    
    /**
     * ??????????????????waf??????????????????
     * ???????????????HttpServletRequest request,HttpServletResponse response
     *       @time 2017-7-5
     */
    @RequestMapping(value="/exportWaf.html",method=RequestMethod.POST)
    public void exportWaf(HttpServletRequest request,HttpServletResponse response) throws Exception{
        try {
        	//??????id
            String orderId = request.getParameter("orderId");
            //???????????? ???????????????
            String radioType = request.getParameter("radioType");
            //????????????
            String beginDate = request.getParameter("beginDate");
            //????????????
            String imgPieLevel = request.getParameter("imgPieLevel").replaceAll(" ", "+");// ???????????????  "+" ????????? " "  
            String imgPieEvent = request.getParameter("imgPieEvent").replaceAll(" ", "+");
            String imgBar = request.getParameter("imgBar").replaceAll(" ", "+");
            String imgOntimeLine = request.getParameter("imgOntimeLine").replaceAll(" ", "+");
            
            //????????????
            Order order = orderService.findOrderById(orderId);
            //??????????????????
            List assets = orderAssetService.findAssetsByOrderId(orderId);
            String webSite = "";
            String ipArray = "";
            String webName = "";
            List<String> dstIpList = new ArrayList();
            if(assets != null && assets.size() > 0){
            	HashMap<String, Object> assetOrder = new HashMap<String, Object>();
            	assetOrder=(HashMap) assets.get(0);
            	webSite = (String) assetOrder.get("addr");
            	webName = (String) assetOrder.get("name");
            	ipArray=(String) assetOrder.get("ipArray");
            	String[] ips = null;   
                ips = ipArray.split(",");
                for (int n = 0; n < ips.length; n++) {
                	String[] ip = ips[n].split(":");
    				dstIpList.add(ip[0]);
                }
                dstIpList.add("219.141.189.183");
            }
            
            
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderId", orderId);
            paramMap.put("radioType", radioType);
            paramMap.put("beginDate", beginDate);
            paramMap.put("webSite", webSite);
            paramMap.put("webName", webName);
            paramMap.put("ipArray", ipArray);
            paramMap.put("dstIpList", dstIpList);

            //??????????????????
            Map<String, String> map = this.getExportData(orderId,paramMap);
            
            String fileDir = new File(base.getFile(), "../../../../../../doc/").getCanonicalPath().replaceAll("%20", " ");
            
            OPCPackage opcPackage = POIXMLDocument.openPackage(fileDir+"/waf.docx");
            XWPFDocument hdt = new XWPFDocument(opcPackage);

            // ??????????????????????????????  
            ExportUtils.replaceText(hdt,map);
            
            
            //???????????????????????????????????????
            String titleStyle = "3";
            String titleName = "";
            titleName = "2.1.1.	?????????????????????";
            ExportUtils.generateTitle(hdt,titleStyle,titleName);
            
            //?????????????????????????????????
            ExportUtils.generatePicture(hdt,imgPieLevel,request,response,264, 312);
            
            //???????????????????????????????????????
            titleName = "2.1.2.	?????????????????????";
            ExportUtils.generateTitle(hdt,titleStyle,titleName);
            
            //?????????????????????????????????
            ExportUtils.generatePicture(hdt,imgBar,request,response,264, 312);
            ExportUtils.generatePicture(hdt,imgPieEvent,request,response,264, 312);

            //??????????????????????????????????????????
            titleName = "2.1.3.	????????????????????????";
            ExportUtils.generateTitle(hdt,titleStyle,titleName);
            
            //????????????
            String[] titles = {"????????????","??????"};
            XWPFTable newt = ExportUtils.generateTable(hdt,titles);
            //??????????????????
            insertDataToTable(newt,2,true,1,paramMap);
            
            
            //???????????????????????????????????????
            titleName = "2.1.4.	?????????????????????";
            ExportUtils.generateTitle(hdt,titleStyle,titleName);
            
            //?????????????????????????????????
            ExportUtils.generatePicture(hdt,imgOntimeLine,request,response,580, 332);
            
            //??????????????????????????????????????????
            titleName = "2.1.5.	????????????????????????";
            ExportUtils.generateTitle(hdt,titleStyle,titleName);
            
            //????????????
            String[] timetitles = {"??????","??????"};
            XWPFTable newtimet = ExportUtils.generateTable(hdt,timetitles);
            //??????????????????
            insertDataToTable(newtimet,2,true,2,paramMap);
            
            //?????????????????????????????????
            titleName = "2.2 ???????????????";
            ExportUtils.generateTitle(hdt,"2",titleName);
            
            
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
            ccpr.setText("?????????IP");
            
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

            //??????????????????
            insertDataToTable(newtable,2,true,3,paramMap);

            
            //??????word??????????????????????????????
            response.reset();
            response.setContentType("application/x-msdownload");
            String outfile = orderId+ "_" + webName +"_????????????-???WAF.docx";
            
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
                else if (userAgent.indexOf("edge") != -1) {
                	rtn = "filename=\"" + new_filename + "\"";
                }
                // Opera?????????????????????filename*
                else if (userAgent.indexOf("opera") != -1) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
                // Safari????????????????????????ISO?????????????????????
                else if (userAgent.indexOf("safari") != -1) {
                    rtn = "filename=\""
                            //+ new String(outfile.getBytes("UTF-8"), "ISO8859-1")
                    		+ outfile
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
//                response.setHeader("Content-Disposition", "attachment;filename=" + new String( outfile.getBytes("utf-8"), "ISO8859-1" ) );
//                response.addHeader("Content-Disposition", "attachment; filename=\"1.docx\"");
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            ServletOutputStream servletOS = response.getOutputStream();
            hdt.write(ostream);
            servletOS.write(ostream.toByteArray());
            servletOS.flush();
            servletOS.close();
                   
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            
        }
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
                  cells.get(0).setColor("ff7e50");
              }else if(list.get(0).equals("???")){
                  cells.get(0).setColor("ffa500");
              }else if(list.get(0).equals("???")){
                  cells.get(0).setColor("1e91ff");
              }else if(list.get(0).equals("??????")){
                  cells.get(0).setColor("40e1d1");
              }else if(list.get(0).equals("??????")){
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
                      cells.get(0).setColor("ff7e50");
                  }else if(list.get(0).equals("???")){
                      cells.get(0).setColor("ffa500");
                  }else if(list.get(0).equals("???")){
                      cells.get(0).setColor("1e91ff");
                  }else if(list.get(0).equals("??????")){
                      cells.get(0).setColor("40e1d1");
                  }else if(list.get(0).equals("??????")){
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
      if(cttc2.getPList().size()>0){
        CTP ctp=cttc2.getPList().get(0);
        if(ctp.getPPr()!=null){
          if(ctp.getPPr().getJc()!=null){
            cttc.getPList().get(0).addNewPPr().addNewJc().setVal(ctp.getPPr().getJc().getVal());
          }
        }
      }
      
      
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
    	String startDate = (String)paramMap.get("beginDate");
    	String timeUnit = (String)paramMap.get("radioType");
    	List<String> dstIpList = (List<String>) paramMap.get("dstIpList");
	    List<List<String>> resultList = new ArrayList<List<String>>();
	    if(tableIndex==1){//???????????????1
	    	String eventStr = WafAPIWorker.getEventTypeCountInTime(startDate,"",timeUnit,dstIpList);
			List listEvent = WafAPIAnalysis.getEventTypeCountNoDecode(eventStr);
			int total = 0;
			for (int i = 0; i < listEvent.size(); i++) {
				List<String> list = new ArrayList<String>();
	 	        Map alarm = (Map) listEvent.get(i);
	 	        String eventType = (String) alarm.get("eventType");
	 	        String count = String.valueOf(alarm.get("count"));
 	            list.add(eventType);
 	            list.add(count);
	 	        resultList.add(list);
	 	        total = total + Integer.parseInt(count);
	 	    }
			List<String> lastrow = new ArrayList<String>();
			lastrow.add("??????");
			lastrow.add(String.valueOf(total));
 	        resultList.add(lastrow);
	    }else if(tableIndex==2){//???????????????2
	    	String unit = "";
	    	if(timeUnit.equals("year")){
    			unit = "month";
    		}else if(timeUnit.equals("month")){
    			unit = "day";
    		}
    		String eventStr = WafAPIWorker.getWafLogWebSecTimeCount(startDate+"-01","",unit,dstIpList);
    		List listTime = WafAPIAnalysis.analysisWafLogWebSecTimeCountList(eventStr);
    		int total = 0;
    		for (int i = 0; i < listTime.size(); i++) {
	 	        List<String> list = new ArrayList<String>();
	 	        Map alarm = (Map) listTime.get(i);
	 	        String count = String.valueOf(alarm.get("count"));
	 	        String time = (String) alarm.get("time");
 	            list.add(time);
 	            list.add(count);
	 	        resultList.add(list);
	 	        total = total + Integer.parseInt(count);
	 	    }
	    	List<String> lastrow = new ArrayList<String>();
			lastrow.add("??????");
			lastrow.add(String.valueOf(total));
 	        resultList.add(lastrow);
	    }else if(tableIndex==3){//???????????????3
	    	String websecStr = WafAPIWorker.getWafLogWebsecSrcIpCountInTime(startDate,"",timeUnit,dstIpList,10);
	    	List websecList = WafAPIAnalysis.getWafLogWebsecSrcIp(websecStr);
	    	if(websecList!=null&&websecList.size()>0){
	    	  for (int i = 0; i < websecList.size(); i++) {
		 	        List<String> list = new ArrayList<String>();
		 	        Map alarm = (Map) websecList.get(i);
		 	        String count = String.valueOf(alarm.get("count"));
		 	        String dstIp = (String) alarm.get("dstIp");
	 	            list.add(dstIp);
	 	            list.add(count);
		 	        resultList.add(list);
		 	    }	
	    	}
	    }
	    return resultList;
    }

    //String webName = "";
    private Map<String, String> getExportData(String orderId, Map<String, Object> paramMap) {
        //????????????
        Order order = orderService.findOrderById(orderId);
        String webSite = (String)paramMap.get("webSite");
        String webName = (String)paramMap.get("webName");
        List<String> dstIpList = (List<String>) paramMap.get("dstIpList");
        //????????????
        SimpleDateFormat odf = new SimpleDateFormat("yyyy/MM/dd");//??????????????????
        String createDate = odf.format(new Date());
        SimpleDateFormat odf1 = new SimpleDateFormat("yyyy???MM???dd???");//??????????????????
        String createDate1 = odf1.format(new Date());
        //??????
        String timeUnit = (String)paramMap.get("radioType");
        String timeChage = "";
        if(timeUnit.equals("month")){
        	timeChage = "??????";
        }else if(timeUnit.equals("year")){
        	timeChage = "??????";
        }
        //????????????
        String beginDate = (String)paramMap.get("beginDate");
        //??????????????????num
        String levelStr = WafAPIWorker.getWafAlertLevelCountInTime(beginDate,"",timeUnit,dstIpList);
        Map maplevel = WafAPIAnalysis.getWafAlertLevelCount(levelStr);
        
        int high = 0;
        int middle = 0;
        int low = 0;
        int count = 0;
        
        if(maplevel != null && maplevel.size() > 0){
			high = Integer.parseInt(maplevel.get("high").toString());
			middle = Integer.parseInt(maplevel.get("mid").toString());
			low = Integer.parseInt(maplevel.get("low").toString());
        }
        count = high + middle + low;
        //??????????????????map
        Map<String, String> map = new HashMap<String, String>();
        
        if(order.getTask_date()!=null){
            order.setTask_datevo(( DateUtils.dateToString(order.getTask_date())));
        }
        map.put("$createDate$", createDate);
        map.put("$createDate1$", createDate1);
        map.put("$JCSJ$", order.getTask_datevo());
        map.put("$type$", timeChage);
        map.put("$beginDate$", beginDate);
        map.put("$LEAKNUM$", String.valueOf(count));
//        map.put("$RISK$", risk);
        map.put("$LOWNUM$", String.valueOf(low));
        map.put("$MIDDLENUM$", String.valueOf(middle));
        map.put("$HIGHNUM$", String.valueOf(high));
        map.put("$webSite$", webSite);
        map.put("$webName$", webName);  
        map.put("$catalog$", "3.2.1 ??????");
        
//        map.put("$REMARKS$", remark);
        return map;

    }
    

    /** 
     * ?????????????????????????????????????????? 
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
                    System.out.println("???????????????");  
                }  
            }  
        }  
        return byteArray;  
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
