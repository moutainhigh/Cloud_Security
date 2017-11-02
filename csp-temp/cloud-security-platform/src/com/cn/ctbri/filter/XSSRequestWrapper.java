package com.cn.ctbri.filter;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

	HttpServletRequest orgRequest = null;

	private String apostrophe = "&#39;";

	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
		this.orgRequest = request;
		
	}
	@Override
	public String getRequestURI(){
		
		String url=orgRequest.getQueryString();
		if(url==null){
			return "";
		}
		String val=cleanXSS(url.trim().toLowerCase());
		//String path = orgRequest.getScheme()+"://"+orgRequest.getServerName()+":"+orgRequest.getServerPort()+orgRequest.getRequestURI()+"?"+val;
		
		return val;
	}
	@Override
	public String getHeader(String paramString) {
		String str = super.getHeader(paramString);
		if (str == null) {
			return null;
		}
		return cleanXSS(str);
	}

	@Override
	public String[] getParameterValues(String paramString) {
		String[] arrayOfString1 = super.getParameterValues(paramString);
		if (arrayOfString1 == null) {
			return null;
		}
		int i = arrayOfString1.length;
		String[] arrayOfString2 = new String[i];
		for (int j = 0; j < i; j++) {
			arrayOfString2[j] = cleanXSS(arrayOfString1[j]);
		}
		return arrayOfString2;
	}

	@Override
	public String getParameter(String paramString) {
		String str = super.getParameter(paramString);
		if (str == null) {
			return null;
		}
		return cleanXSS(str);
	}

	private String cleanXSS(String paramString) {
		
		if (paramString == null) {
			return "";
		}
		String str =  java.net.URLDecoder.decode(paramString);
		
		String reg = "(>|<|\\()|%|--|"  
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|limit|execute)\\b)";
		Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Pattern localPattern = Pattern.compile("<script>(.*?)</script>",
				Pattern.CASE_INSENSITIVE);
		localPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", 42);
		localPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 42);
		localPattern = Pattern.compile("</script>", 2);
		localPattern = Pattern.compile("<script(.*?)>", 42);
		localPattern = Pattern.compile("eval\\((.*?)\\)", 42);
		localPattern = Pattern.compile("expression\\((.*?)\\)", 42);
		localPattern = Pattern.compile("javascript:", 2);
		localPattern = Pattern.compile("vbscript:", 2);
		localPattern = Pattern.compile("onload(.*?)=", 42);
		//boolean flag=sqlPattern.matcher(str).find();
		if(sqlPattern.matcher(str).find()){
			str= "true";
		}else if(localPattern.matcher(str).find()){
			str="true";
		}
		return str;
	}

	/**
	 * 获取最原始的request
	 * 
	 * @return
	 */
	public HttpServletRequest getOrgRequest() {
		return orgRequest;
	}

	/**
	 * 获取最原始的request的静态方法
	 * 
	 * @return
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
		if (req instanceof XSSRequestWrapper) {
			return ((XSSRequestWrapper) req).getOrgRequest();
		}

		return req;
	}

	@Override
	public Map getParameterMap() {
		Map<String, String[]> paramMap = super.getParameterMap();
		Set<String> keySet = paramMap.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String[] str = paramMap.get(key);
			for (int i = 0; i < str.length; i++) {
				str[i] = cleanXSS(str[i]);
			}
		}
		return paramMap;
	}
}
