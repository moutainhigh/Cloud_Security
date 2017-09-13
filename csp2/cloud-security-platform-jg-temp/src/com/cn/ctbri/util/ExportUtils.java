package com.cn.ctbri.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import sun.misc.BASE64Decoder;


public class ExportUtils {

	//生成标题
	public static void generateTitle(XWPFDocument hdt, String titleStyle, String titleName) {
		XWPFParagraph paragraph21 = hdt.createParagraph();
        paragraph21.setStyle(titleStyle);  
        //在段落中新插入一个run,这里的run我理解就是一个word文档需要显示的个体,里面可以放文字,参数0代表在段落的最前面插入  
        XWPFRun run21 = paragraph21.insertNewRun(0);  
        //设置run内容   
        run21.setBold(true); 
        run21.setText(titleName);
	}

	//生成图片
	public static void generatePicture(XWPFDocument hdt, String img,
			HttpServletRequest request, HttpServletResponse response, int width, int height) {
		try {
			String userName = System.getProperty("user.name");
	        //获取文件路径
	        String filePath = request.getSession().getServletContext().getRealPath("/source/chart");
	        File file = new File(filePath);
	        if(!file.exists()){
	            file.mkdir();
	        }
	        String fileName = filePath +"\\"+ System.currentTimeMillis()+".png";
	       
	        createImage(request, response, fileName, img);
	        
	        XWPFParagraph p = hdt.createParagraph();
	        String blipId = p.getDocument().addPictureData(
	                new FileInputStream(new File(fileName)),
	                Document.PICTURE_TYPE_PNG);
        
			createPicture(blipId,
			        hdt.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), width, height, p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//生成table
	public static XWPFTable generateTable(XWPFDocument hdt, String[] titles) {
		XWPFTable newt = hdt.createTable();
        //默认TblW的type属性为STTblWidth.AUTO,即自动伸缩。所以要调整为指定类型：STTblWidth.DXA
        CTTblPr tlPr = newt.getCTTbl().getTblPr();
        tlPr.getTblW().setType(STTblWidth.DXA);
        tlPr.getTblW().setW(new BigInteger("8500"));
        
        XWPFTableRow rowr = newt.getRow(0);
        for (int i = 0; i < titles.length; i++) {
			if(i==0){
				XWPFTableCell ccrr = rowr.getCell(0);
				XWPFParagraph ccprr=ccrr.getParagraphs().get(0);
		        XWPFRun ccpr1 = ccprr.createRun();
		        ccpr1.setFontSize(10);
		        ccpr1.setBold(true);
		        ccpr1.setColor("000000");
		        ccpr1.setText(titles[i]);
			}else{
				XWPFTableCell c12 =rowr.createCell();
		        XWPFParagraph ccp12=c12.getParagraphs().get(0);
		        XWPFRun ccpr12 = ccp12.createRun();
		        ccpr12.setFontSize(10);
		        ccpr12.setBold(true);
		        ccpr12.setColor("000000");
		        ccpr12.setText(titles[i]);
			}
		}
        
        XWPFTableRow two22 = newt.createRow();
        for (int i = 0; i < titles.length; i++) {
        	two22.getCell(i).setText(String.valueOf(i+1));
        }
        return newt;
	}

	
	public static void createImage(HttpServletRequest request, HttpServletResponse response,
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

    public static void createPicture(String blipId, int id, int width, int height,
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

	public static void replaceText(XWPFDocument hdt, Map<String, String> map) {
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
	}



}

	
