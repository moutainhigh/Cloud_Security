package com.cn.ctbri.util;

public class IPCheck {
	//检查ip是否在范围内
	public static boolean ipIsValid(String ipStart, String ipEnd, String ip) {   
        try {
			if (ipStart == null || ipEnd == null || ip == null)   
				return false; 
			ipStart = ipStart.trim();   
			ipEnd = ipEnd.trim();
			ip = ip.trim();   
			final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";   
  
			if (!ipStart.matches(REGX_IP) || !ip.matches(REGX_IP) || !ipEnd.matches(REGX_IP))   
			    return false;   

			String[] sips = ipStart.split("\\.");   
			String[] sipe = ipEnd.split("\\.");   
			String[] sipt = ip.split("\\.");   
			long ips = 0L, ipe = 0L, ipt = 0L;   
			for (int i = 0; i < 4; ++i) {   
			    ips = ips << 8 | Integer.parseInt(sips[i]);   
			    ipe = ipe << 8 | Integer.parseInt(sipe[i]);   
			    ipt = ipt << 8 | Integer.parseInt(sipt[i]);   
			}   
/*        if (ips > ipe) {   
			    long t = ips;   
			    ips = ipe;   
			    ipe = t;   
			}  */ 
			return ips <= ipt && ipt <= ipe;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}   
    }  
	
}
