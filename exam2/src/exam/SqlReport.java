package exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SqlReport {
	private static String[] getNameValue(List<List<String>> list,String name,int nameColumn,boolean isNameMarked,String value,int valueColumn,boolean isValueMarked){
        StringBuffer typeBuffer = new StringBuffer("var "+name+" = [");
        StringBuffer countBuffer = new StringBuffer("var "+value+" = [");
        if (list != null) {
        	int size = list.size();
            for (int i = 0; i < size; i++) {
                List<String> cellList = list.get(i);
                StringBuffer formSb = new StringBuffer();
                for (int j = 0; j < cellList.size(); j++) {
                    // System.out.print("    第" + (j + 1) + "列值：");
                    //System.out.print("    " + cellList.get(j));
                	if (j==(nameColumn-1)) {
                		if (isNameMarked) {
                			typeBuffer.append("'"+cellList.get(j)+"'");	
						}else {
                			typeBuffer.append(cellList.get(j));	
						}
						
					}else if (j==(valueColumn-1)) {
						int jCount = (int)Float.parseFloat(cellList.get(j));
						if (isValueMarked) {
							countBuffer.append("'"+jCount+"'");
						} else {
							countBuffer.append(jCount);
						}
					}
                	if(j==cellList.size()-1){
                		formSb.append(cellList.get(j));
                		continue;
                	}
                	formSb.append(cellList.get(j)+"\t");
                }
                System.out.println(formSb);
                if (i==(size-1)) {
					continue;
				}
                typeBuffer.append(",");
                countBuffer.append(",");
            }

        }
        typeBuffer.append("];");
        countBuffer.append("];");
        String[] strings={typeBuffer.toString(),countBuffer.toString()};
        return strings;
    }
	
	private static String[] getTopNameValue(List<List<String>> list,String name,int nameColumn,boolean isNameMarked,String value,int valueColumn,boolean isValueMarked,int topNum){
      StringBuffer typeBuffer = new StringBuffer("var "+name+" = [");
      StringBuffer countBuffer = new StringBuffer("var "+value+" = [");
      if (list != null) {
    	  int size = list.size();
          for (int i = 0; i<topNum&&i < size; i++) {
              List<String> cellList = list.get(i);
              for (int j = 0; j < cellList.size(); j++) {
              	if (j==(nameColumn-1)) {
              		if (isNameMarked) {
              			typeBuffer.append("'"+cellList.get(j)+"'");	
						}else {
              			typeBuffer.append(cellList.get(j));	
						}
						
					}else if (j==(valueColumn-1)) {
						int jCount = (int)Float.parseFloat(cellList.get(j));
						if (isValueMarked) {
							countBuffer.append("'"+jCount+"'");
						} else {
							countBuffer.append(jCount);
						}
						
					}
              }
              if (i==(list.size()-1)||i==topNum-1) {
					continue;
				}
              typeBuffer.append(",");
              countBuffer.append(",");
          }

      }
      typeBuffer.append("];");
      countBuffer.append("];");
      System.out.println(typeBuffer.toString());
      System.out.println(countBuffer.toString());
      String[] strings={typeBuffer.toString(),countBuffer.toString()};
      return strings;
  }
	 private static List<List<String>> getSqlList(String sqlString) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://219.141.189.186:30117/waflogdata","root","mssp@ctbri&");
        Statement stmt = conn.createStatement(); //创建Statement对象
        System.out.println("成功连接到数据库！");
        ResultSet rs = stmt.executeQuery(sqlString);
        int columnCount = rs.getMetaData().getColumnCount();

        List<List<String>> list = new ArrayList<List<String>>();
        while (rs.next()) {
            List<String> inList = new ArrayList<String>();
        	for (int i = 1; i <= columnCount; i++) {
				inList.add(rs.getString(i));
			}
        	list.add(inList);
        }
        return list;
	}
	private static void printStrings(String[] strings) {
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	private static void setStrings(String[] strings,List<List<String>> list) {
		for (String string : strings) {
			List<String> stringList = new ArrayList<String>();
			stringList.add(string);
			list.add(stringList);
		}
	}

	public static void main(String[] args) {
		try {
			String startDate = "2016-10-01 00:00:00.0";
			String endDate = "2017-10-11 00:00:00";
			
			String domainString = "'www.crcc.cn','english.crcc.cn','pxzx.crcc.cn','youth.crcc.cn','www.crccy.cn','www.tcjl.com.cn','www.crucg3.com','crfc.crcc.cn','www.crccfc.com.cn'";
			String domainString2 = "'www.criponline.com'";
			
			String name="中铁建十一假期";
			
			//ip
			//List<List<String>> ipList = getSqlList("select src_ip,count(*) as count from t_waf_log_websec where domain in ("+domainString+") and stat_time between '"+startDate+"' and '"+endDate+"' GROUP BY src_ip ORDER BY count desc");
			List<List<String>> ipList = getSqlList("select src_ip,count(*) as count from t_waf_log_websec where domain in ("+domainString+") and stat_time between '"+startDate+"' and '"+endDate+"' GROUP BY src_ip ORDER BY count desc");

			//ip地理信息
			//LocationUtil.location(ipList);
			String[] ipString = getNameValue(ipList,"ip_name", 1, true, "ip_data", 2,false); 
            
			String[] ipTop10String = getTopNameValue(ipList,"ip_name", 1, true, "ip_data", 2,false,10);
			//事件类型
            //List<List<String>> eventList = getSqlList("SELECT t.event_type,e.typeValue,count(t.event_type) as count FROM t_waf_log_websec as t LEFT JOIN t_websec_eventtype as e on t.event_type=e.typeName WHERE domain in ("+domainString+") and stat_time between '"+startDate+"' and '"+endDate+"' GROUP BY event_type order by count desc ");
            List<List<String>> eventList = getSqlList("SELECT t.event_type,e.typeValue,count(t.event_type) as count,t.src_ip FROM t_waf_log_websec as t LEFT JOIN t_websec_eventtype as e on t.event_type=e.typeName WHERE domain in ("+domainString+") and stat_time between '"+startDate+"' and '"+endDate+"' GROUP BY event_type order by count desc ");
            
			String[] eventString = getNameValue(eventList,"event_name", 2, true, "event_data", 3, false);
            
            String[] eventTop10String = getTopNameValue(eventList,"event_name", 2, true, "event_data", 3, false,10);
            //时间
            List<List<String>> timeList = getSqlList("SELECT date_format(stat_time,'%Y-%m-%d') as time,count(*),src_ip FROM t_waf_log_websec WHERE domain in ("+domainString+") and stat_time between '"+startDate+"' and '"+endDate+"' group by time");
            String[] timeString = getNameValue(timeList,"time", 1, true, "time_data", 2, false);
            
            //等级
            List<List<String>> levelList = getSqlList("SELECT alertlevel,count(*) as count FROM t_waf_log_websec WHERE domain in ("+domainString+") and stat_time between '"+startDate+"' and '"+endDate+"'  GROUP BY alertlevel");
            String[] levelString = getNameValue(levelList, "level", 1, true, "level_data", 2, false);
            
            //网站统计
            List<List<String>> domainCountList = getSqlList("SELECT domain,count(*) as count FROM t_waf_log_websec WHERE domain in ("+domainString+") and stat_time between '"+startDate+"' and '"+endDate+"' GROUP BY domain");
            String[] domainCountString = getNameValue(domainCountList, "level", 1, true, "level_data", 2, false);
            
            //网站等级统计
            List<List<String>> domainLevelCountList = getSqlList("SELECT domain,alertlevel,count(*) as count FROM t_waf_log_websec WHERE domain in ("+domainString+") and stat_time between '"+startDate+"' and '"+endDate+"' GROUP BY domain,alertlevel");
            String[] domainLevelCountString = getNameValue(domainLevelCountList, "level", 2, true, "level_data", 3, false);
            //打印chart图表变量语句
            printStrings(ipString);
            printStrings(ipTop10String);
            printStrings(eventString);
            printStrings(eventTop10String);
            printStrings(timeString);
            printStrings(levelString);
            
            
			//chart图表变量语句放入复合list中
           List<List<String>> chartList = new ArrayList<List<String>>();
           setStrings(levelString, chartList);
           setStrings(ipString, chartList);
           setStrings(ipTop10String, chartList);
           setStrings(timeString, chartList);
           setStrings(eventString, chartList);
           setStrings(eventTop10String, chartList);

           //map中存入所有复合List的数据
           HashMap hashMap = new HashMap();
           hashMap.put("ip", ipList);
           hashMap.put("event", eventList);
           hashMap.put("time", timeList);
           hashMap.put("level", levelList);
           hashMap.put("domainCount", domainCountList);
           hashMap.put("chart", chartList);
           
           /*
            * 导出到excel
            * 导出规则：
            * hashmap.key为表的sheet名
            * hashmap.value为表的内容，value的类型为"复合List"=List<List<String>>
            * list=List<List<String>>,list.get(i)为第i+1行的内容，数据类型为List<String>
            * list=List<String>,list.get(j)为第i+1行第j+1个单元格的数据，类型为String
            */
           SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
           String fileName = name+"_"+df.format(new Date());
           String pathString = "/Users/shaozhenya/Desktop/"+fileName+".xls";
           //ExportExcel.Export(hashMap,pathString);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
