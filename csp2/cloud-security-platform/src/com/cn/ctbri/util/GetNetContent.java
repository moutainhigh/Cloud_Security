package com.cn.ctbri.util;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.Node;
import org.htmlparser.Parser;

import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;
import org.htmlparser.visitors.TextExtractingVisitor;

//http://blog.sina.com.cn/s/blog_7f95d0c40100xxsl.html
public class GetNetContent {
	public static void main(String[] args){    
//    NodeList rt= getNodeList("http://localhost:80/cloud-security-platform/"); 
//    System.out.println(rt.toHtml());
 }   

 public static String getNodeList(String url){
	 
    Parser parser = null;
    TextExtractingVisitor visitor = null;
    try {
         parser = new Parser(url);
//         parser.setEncoding("GBK");
//         parser.setEncoding(parser.getLexer().getPage().getQICHAODEFAULT_CHARSET());
         visitor = new TextExtractingVisitor();
         parser.visitAllNodesWith(visitor);
         
//         visitor = new HtmlPage(parser);
//         parser.visitAllNodesWith(visitor);
    } catch (Exception e) {
        try {
            parser.setEncoding("UTF-8");
            parser.reset();
            parser.visitAllNodesWith(visitor);
        } catch (ParserException e1) {
            e1.printStackTrace();
        }
    }
    String nodeList = visitor.getExtractedText();
    
    return nodeList;
 }
}
 /*
	 public static void main(String[] args){    
         String url="http://localhost:8080/cloud-security-platform/";
         ArrayList<String> rt= getNodeList(url);      
         for (int i = 0; i < rt.size(); i++){
            System.out.println(rt.get(i));
          }      
     }         
     public static ArrayList<String> getNodeList(String url){
         final ArrayList<String> result=new ArrayList<String>();
         Parser parser = null;
         NodeList nodeList=null;
         try {
            parser = new Parser(url);
            parser.setEncoding("GBK");
            nodeList = parser.parse(
                   new NodeFilter(){
                       public boolean accept(Node node){
                           Node need=node;
                          if(getStringsByRegex(node.getText())){
                              for(int i=0;i<6;i++){
                              result.add(need.toPlainTextString());                          
                              need=need.getPreviousSibling().getPreviousSibling();
                              }
                              return true;
                          }                          
                          return false;
                       }                   
                	  }
            );               
         }catch (ParserException e) {
            e.printStackTrace();
         }
         return result;
     }    
     public static boolean getStringsByRegex(String txt) {
         String regex="td class=\"no\"";   
         Pattern p = Pattern.compile(regex);
         Matcher m = p.matcher(txt);
         if (m.find()){
             return true;
         }  
         return false;
     }
*/
