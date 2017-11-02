package com.cn.ctbri.entity;


public class MobileInfo  implements java.io.Serializable{
private static final long serialVersionUID = 1L;
private String MobileNumber;//手机号
private int Times;//次数
private String sendDate;//发送时间
public String getMobileNumber() {
	return MobileNumber;
}
public void setMobileNumber(String mobileNumber) {
	MobileNumber = mobileNumber;
}
public int getTimes() {
	return Times;
}
public void setTimes(int times) {
	Times = times;
}
public String getSendDate() {
	return sendDate;
}
public void setSendDate(String sendDate) {
	this.sendDate = sendDate;
}


}
