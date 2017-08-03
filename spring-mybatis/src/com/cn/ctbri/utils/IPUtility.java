package com.cn.ctbri.utils;



import java.math.BigInteger;

//ip相关工具类
public class IPUtility {
	//ip转长整型
	/**
	 * ip转长整型
	 * @param ipv4
	 * @return 
	 */
    public static long ip2long(String ipv4)  
    {  
  
        String[] splits = ipv4.split("\\.");  
        long l = 0;  
        l = l + (Long.valueOf(splits[0], 10)) << 24;  
        l = l + (Long.valueOf(splits[1], 10) << 16);  
        l = l + (Long.valueOf(splits[2], 10) << 8);  
        // yunsuanfu youxianji  
        l = l + (Long.valueOf(splits[3], 10));  
        //System.out.println(Long.toBinaryString(l));
        //System.out.println(l);
        return l;  
    }
    public static void main(String[] args){
    	long a  = ip2long("61.49.22.114");
    	System.out.println(a);
    }
    /*
    public static final long ip2Long(final String ip) { 
    	String ipAddr = ip.trim();
    	String[] ipNums = ipAddr.split("\\.");  
    	
    	return (Long.parseLong(ipNums[0]) << 24)  
    	          + (Long.parseLong(ipNums[1]) << 16)  
    	           + (Long.parseLong(ipNums[2]) << 8)  
    	            + (Long.parseLong(ipNums[3]));  
    }  
    */
    /**
     * long转ipv4
     * @param l
     * @return
     */
    public static String long2ip(long l)  
    {  
        String ip = "";  
        ip = ip + (l >>> 24);  
  
        ip = ip + "." + ((0x00ffffff & l) >>> 16);  
        ip = ip + "." + ((0x0000ffff & l) >>> 8);  
        ip = ip + "." + (0x000000ff & l);  
        return ip;  
    }  
    //ipv6转bigInteger
    /**
     * ipv6转BigInteger
     * @param ipv6
     * @return
     */
    public static BigInteger ipv6toInt(String ipv6)  
    {  
  
        int compressIndex = ipv6.indexOf("::");  
        if (compressIndex != -1)  
        {  
            String part1s = ipv6.substring(0, compressIndex);  
            String part2s = ipv6.substring(compressIndex + 1);  
            BigInteger part1 = ipv6toInt(part1s);  
            BigInteger part2 = ipv6toInt(part2s);  
            int part1hasDot = 0;  
            char ch[] = part1s.toCharArray();  
            for (char c : ch)  
            {  
                if (c == ':')  
                {  
                    part1hasDot++;  
                }  
            }  
            // ipv6 has most 7 dot  
            return part1.shiftLeft(16 * (7 - part1hasDot )).add(part2);  
        }  
        String[] str = ipv6.split(":");  
        BigInteger big = BigInteger.ZERO;  
        for (int i = 0; i < str.length; i++)  
        {  
            //::1  
            if (str[i].isEmpty())  
            {  
                str[i] = "0";  
            }  
            big = big.add(BigInteger.valueOf(Long.valueOf(str[i], 16))  
                    .shiftLeft(16 * (str.length - i - 1)));  
        }  
        return big;  
    }  
    /**
     * bigInteger转ipv6 
     * @param big
     * @return
     */
    public static String int2ipv6(BigInteger big)  
    {  
        String str = "";  
        BigInteger ff = BigInteger.valueOf(0xffff);  
        for (int i = 0; i < 8 ; i++)  
        {  
            str = big.and(ff).toString(16) + ":" + str;  
              
            big = big.shiftRight(16);  
        }  
        //the last :  
        str = str.substring(0, str.length() - 1);  
          
        return str.replaceFirst("(^|:)(0+(:|$)){2,8}", "::");  
    } 
    
    //判断是否标准ip地址（ipv4）
    public static String ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return text;
            } else {
                // 返回判断信息
                return text + "\n不是一个合法的IP地址！";
            }
        }
        // 返回判断信息
        return "请输入要验证的IP地址！";
    }
}
