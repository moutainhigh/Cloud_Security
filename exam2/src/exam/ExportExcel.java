package exam;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.StringUtil;

/**
 * 导出Excel公共方法
 */
public class ExportExcel {
	 public static void Export(HashMap<String, List> hashMap,String path){
		 	// 声明一个工作薄
	        HSSFWorkbook wb = new HSSFWorkbook();
	        for (Entry<String, List> entry: hashMap.entrySet()) {
				HSSFSheet sheet = wb.createSheet(entry.getKey());
				List list = entry.getValue();
				if (list != null) {
			    	  int size = list.size();
			          for (int i = 0; i < size; i++) {
			              //System.out.print("第" + (i) + "行");
			        	  HSSFRow row = sheet.createRow(i);
			              List<String> cellList = (List<String>)list.get(i);
			              for (int j = 0; j < cellList.size(); j++) {
			                  // System.out.print("    第" + (j + 1) + "列值：");
			                  //System.out.print("    " + cellList.get(j));
			            	  
			            	  HSSFCell cell = row.createCell(j);
			            	  String cellValueString = cellList.get(j);
			                  Boolean isNum = cellValueString.matches("^(-?\\d+)(\\.\\d+)?$");//data是否为数值型
			                  Boolean isInteger=cellValueString.matches("^[-\\+]?[\\d]*$");//data是否为整数
			                  System.out.print(cellValueString+" "+isNum);
			                  /*if (isNum) {
			                	  HSSFCellStyle cellStyle = wb.createCellStyle();
			                      HSSFDataFormat df = wb.createDataFormat(); // 此处设置数据格式
			                      if (isInteger) {
			                    	  System.out.println(" "+"hahahaha");
			                          cellStyle.setDataFormat(df.getBuiltinFormat("#,#0"));//数据格式只显示整数
			                      }else{
			                          cellStyle.setDataFormat(df.getBuiltinFormat("#,##0.00"));//保留两位小数点
			                      }                   
			                      // 设置单元格格式
			                      cell.setCellStyle(cellStyle);
			                      cell.setCellValue(Double.parseDouble(cellValueString));
			                  }else {
			                	  HSSFCellStyle cellStyle = wb.createCellStyle();
			                      cell.setCellStyle(cellStyle);
			                      // 设置单元格内容为字符型
			                      cell.setCellValue(cellValueString);
			                  }*/
			            	  cell.setCellValue(cellList.get(j));
			              }
			          }

			      }
			} 
	        try {
	            //默认导出到E盘下
	            FileOutputStream out = new FileOutputStream(path);
	            wb.write(out);
	            out.close();
	            JOptionPane.showMessageDialog(null, "导出成功!");
	        } catch (FileNotFoundException e) {
	            JOptionPane.showMessageDialog(null, "导出失败!");
	            e.printStackTrace();
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "导出失败!");
	            e.printStackTrace();
	        }
	    }
}

