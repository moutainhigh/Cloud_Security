package exam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class LocationUtil {
	public static String locationFormat(String valueString) {
        String[] array = valueString.split(" ");
        StringBuffer sb = new StringBuffer();
        List<String> strings = null;
    	if (!array[0].equals("中国")) {
			sb.append(array[0]);
		} else if (array[1].contains("市")) {
			sb.append(array[0]);
			sb.append("/"+array[1]);
		} else {
			sb.append(array[0]);
			sb.append("/"+array[1]);
			if (array.length>2) {
				sb.append("/"+array[2]);
			}
			
		}
    	return sb.toString();
	}
	public static void location(List<List<String>> ipList) {
		RequestUtil request = new RequestUtil();
		System.out.println("------------------");
		for (List<String> list : ipList) {
			String ipString = list.get(0);
			String locationString = request.requestByGetMethod(ipString);
			System.out.println(ipString+"\t"+locationString);
			String lFString = LocationUtil.locationFormat(locationString);
			list.add(lFString);
		}
		System.out.println("--------------------");
	}
}
