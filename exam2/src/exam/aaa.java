package exam;
public class aaa {
	public static String exChange(String str){  
	    StringBuffer sb = new StringBuffer();  
	    if(str!=null){  
	        for(int i=0;i<str.length();i++){  
	            char c = str.charAt(i);  
	            if(Character.isUpperCase(c)){  
	                sb.append(Character.toLowerCase(c));  
	            }else if(Character.isLowerCase(c)){  
	                sb.append(Character.toUpperCase(c));   
	            }else {
					sb.append(c);
				}
	        }  
	    }  
	      
	    return sb.toString();  
	} 
	public static void main(String[] args) {
		System.out.println("主干");
		System.out.println("Hello World");
		System.out.println("分支");
	}
}
