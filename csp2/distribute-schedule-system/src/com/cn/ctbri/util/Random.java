package com.cn.ctbri.util;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-4
 * 描        述：  获取六位随机数类
 * 版        本：  1.0
 */
public class Random {
	/**
	 * 功能描述：获取六位随机数
	 * 参数描述： 无
	 *		 @time 2014-12-31
	 */
    public static long code() {
        int n = 0 ;
        while(n < 100000){
                 n = (int)(Math.random()*1000000);
        }
        return n;
    }

    /**
     * 功能描述：获取8位随机数
     * 参数描述： 无
     *       @time 2014-12-31
     */
    public static int eightcode() {
        int n = 0 ;
        while(n < 10000000){
                 n = (int)(Math.random()*100000000);
        }
        return n;
    }
    
    /**
     * 功能描述：获取10位随机数
     * 参数描述： 无
     *       @time 2014-12-31
     */
    public static long tencode() {
        long n = 0 ;
        while(n < 1000000000){
                 n = (long)(Math.random()*100000000*100);
        }
        return n;
    }

}