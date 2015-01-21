package com.cn.ctbri.util;
import org.htmlparser.Parser;

import org.htmlparser.util.NodeList;

import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

//http://blog.sina.com.cn/s/blog_7f95d0c40100xxsl.html
public class GetNetContent {
	public static void main(String[] args){    
    NodeList rt= getNodeList("http://localhost:8080/cloud-security-platform/"); 
    System.out.println(rt.toHtml());
 }   

 public static NodeList getNodeList(String url){
	 
    Parser parser = null;
    HtmlPage visitor = null;
    try {
         parser = new Parser(url);
         parser.setEncoding("GBK");
         visitor = new HtmlPage(parser);
         parser.visitAllNodesWith(visitor);
    } catch (ParserException e) {
         e.printStackTrace();
    }
    NodeList nodeList = visitor.getBody();
    return nodeList;
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


	
*/}
