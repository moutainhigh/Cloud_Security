package com.cn.ctbri.util;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class GetNetContent {
	public  static String getNodeList(String url){  
		Parser parser = null;
	    String body = "";//正文  
	    try{  
	        parser = new Parser(url);//要解析的网页  
	        parser.setEncoding("UTF-8");//设置编码  
//	        NodeFilter filter_text = new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","safeBox"));//正文节点过滤  
	        NodeFilter filter_text = new AndFilter();//不过滤
	        
	        parser.reset();//重置  
	        NodeList nodelist = parser.parse(filter_text);//过滤出符合filter_text的节点LIST  
	        Node[] nodes = nodelist.toNodeArray();//转化为数组  
	        StringBuffer buftext = new StringBuffer();  
	        String line = null;  
	        for(int i=0; i<nodes.length; i++){//循环加到buftext上  
	            line = nodes[i].toHtml();  
	             if(line != null){  
	                 buftext.append(line);  
	             }  
	        }  
	        body = buftext.toString();  
	        System.out.println(body);//输出  
	    }catch(Exception e){
	    	try {
	            parser.setEncoding("UTF-8");
	            parser.reset();
	        } catch (ParserException e1) {
	            e1.printStackTrace();
	        }
	        e.printStackTrace();  
	    }  
	    return body;
	 }   
}
