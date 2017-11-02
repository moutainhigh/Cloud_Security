package com.cn.ctbri.util;

public class passwordLevelUtil {
	//密码高级设置验证
	public static int passwordLevel(String password) {
		 int Modes = 0;
		 for (int i = 0; i < password.length(); i++) {
		  Modes |= CharMode(password.charAt(i));
		 }
		 return bitTotal(Modes);
	}

	//CharMode函数
	private static int CharMode(int iN) {
	 if (iN >= 48 && iN <= 57)//数字
	  return 1;
	 if (iN >= 65 && iN <= 90) //大写字母
	  return 2;
	 if ((iN >= 97 && iN <= 122) || (iN >= 65 && iN <= 90))
	//大小写
	  return 4;
	 else
	  return 8; //特殊字符
	}

	//bitTotal函数
	private static int  bitTotal(int num) {
		String numOctal = Integer.toBinaryString(num);
	 int modes = 0;
	 for (int i = 0; i < numOctal.length(); i++) {
		 String index = numOctal.charAt(i)+"";
	  if (index.equals("1")) modes++;
	 }
	 return modes;
	}
}
