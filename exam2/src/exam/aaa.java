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
<<<<<<< .working
		
		System.out.println("1");
		1
		2
		3
		4
		5
		
=======
		System.out.println(exChange("Hello World 2"));
>>>>>>> .merge-right.r4638
	}
}
