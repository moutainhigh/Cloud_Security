package com.cn.ctbri.vo;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.cn.ctbri.entity.Attack;
import com.cn.ctbri.entity.AttackCount;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.NumberUtils;

public class AttackVO {
	/**
	 * 通过构造方法实现对象的拷贝
	 * @param attack
	 */
	public AttackVO(Attack attack){
		this.id=attack.getId();
		Date date=attack.getStartTime();
		if(date!=null){
			this.startTime=DateUtils.dateToString(attack.getStartTime());
		}
		this.srcName=attack.getSrcName();
		this.srcIP=attack.getSrcIP();
		this.desName=attack.getDesName();
		this.desIP=convertIp(attack.getDesIP());
		this.type=attack.getType();
		this.typeCode=attack.getTypeCode();
		this.port=attack.getPort();
		double srcLongitudeTemp=Double.parseDouble(attack.getSrcLongitude());
		double srcLatitudeTemp=Double.parseDouble(attack.getSrcLatitude());
		double desLongitudeTemp=Double.parseDouble(attack.getDesLongitude());
		double desLatitudeTemp=Double.parseDouble(attack.getDesLatitude());
		this.srcLongitude=NumberUtils.getPointAfterOneNumber(srcLongitudeTemp);
		this.srcLatitude=NumberUtils.getPointAfterOneNumber(srcLatitudeTemp);
		this.desLongitude=NumberUtils.getPointAfterOneNumber(desLongitudeTemp);
		this.desLatitude=NumberUtils.getPointAfterOneNumber(desLatitudeTemp);
		this.attackCount=attack.getAttackCount();
	}
	//时间，攻击方，IP,被攻击方，IP，攻击类型，类型编码，端口,经度，纬度
		private long id;
		private String startTime;
		private String srcName;
		private String srcIP;
		private String desName;
		private String desIP;
		private String type;
		private Integer typeCode;
		private String port;
		private String srcLongitude;
		private String srcLatitude;
		private String desLongitude;
		private String desLatitude;
		private ArrayList<AttackCount> attackCount;
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getSrcName() {
			return srcName;
		}
		public void setSrcName(String srcName) {
			this.srcName = srcName;
		}
		public String getSrcIP() {
			return srcIP;
		}
		public void setSrcIP(String srcIP) {
			this.srcIP = srcIP;
		}
		public String getDesName() {
			return desName;
		}
		public void setDesName(String desName) {
			this.desName = desName;
		}
		public String getDesIP() {
			return desIP;
		}
		public void setDesIP(String desIP) {
			this.desIP = desIP;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Integer getTypeCode() {
			return typeCode;
		}
		public void setTypeCode(Integer typeCode) {
			this.typeCode = typeCode;
		}
		public String getPort() {
			return port;
		}
		public void setPort(String port) {
			this.port = port;
		}
		public String getSrcLongitude() {
			return srcLongitude;
		}
		public void setSrcLongitude(String srcLongitude) {
			this.srcLongitude = srcLongitude;
		}
		public String getSrcLatitude() {
			return srcLatitude;
		}
		public void setSrcLatitude(String srcLatitude) {
			this.srcLatitude = srcLatitude;
		}
		public String getDesLongitude() {
			return desLongitude;
		}
		public void setDesLongitude(String desLongitude) {
			this.desLongitude = desLongitude;
		}
		public String getDesLatitude() {
			return desLatitude;
		}
		public void setDesLatitude(String desLatitude) {
			this.desLatitude = desLatitude;
		}
		public ArrayList<AttackCount> getAttackCount() {
			return attackCount;
		}
		public void setAttackCount(ArrayList<AttackCount> attackCount) {
			this.attackCount = attackCount;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		@Override
		public String toString() {
			return "[srcName:" + srcName + ", srcIP:" + srcIP + ", desName:"
					+ desName + ", desIP:" + desIP + ", type:" + type
					+ ", typeCode:" + typeCode + ", port:" + port
					+ ", srcLongitude:" + srcLongitude + ", srcLatitude:"
					+ srcLatitude + ", desLongitude:" + desLongitude
					+ ", desLatitude:" + desLatitude + "]";
		}
		
		public String convertIp(String ip){
			if(StringUtils.isNotEmpty(ip)){
				String ipArray[]=ip.split("\\.");
				ipArray[1]="*";
				ipArray[2]="*";
				StringBuilder sb=new StringBuilder();
				sb.append(ipArray[0]+".");
				sb.append(ipArray[1]+".");
				sb.append(ipArray[2]+".");
				sb.append(ipArray[3]);
				return sb.toString();
			}
			return null;
		}
		
}
