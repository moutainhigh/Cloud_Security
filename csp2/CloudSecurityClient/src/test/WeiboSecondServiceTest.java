package test;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class WeiboSecondServiceTest {

	/**
	 * 更新微博二次转发数
	 */
	public void testUpdateWeiboSecondByUeridBowenId() {
		String domin = "http://yaifeng.eicp.net/MicroBlogClientServer/rs/WeiboSecondService/updateWeiboSecondByUeridBowenId";
		String userid="10004998";
		String jsonback=String.format("[{'id': '3741034523906214','comments': '16','reposts': '38'}]");
		String blogIds="3741034523906214,3741036616708646";
		
		Client client = new Client();
		
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("userid", userid);
		form.add("jsonback", jsonback);
		form.add("blogIds", blogIds);
		
		String loginResult = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(String.class, form);
		System.out.println(loginResult);
	}
	
	/**
	 * 注册 post
	 */
	public void myRegist(){
		String domin = "http://yaifeng.eicp.net/MicroBlogClientServer/rs/UserRegForWeiboService/registerUserAndInsertPlugin";

		
		Client client = new Client();
		
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		
		form.add("username","mylogin");
		form.add("account","mylogin@126.com");
		form.add("password","111111");
		form.add("deptid","001001001");
		form.add("orgname","001001");
		form.add("phone","13222221111");
		form.add("nickname","butt");
		form.add("weibo","12345");
		form.add("icoPath","http://appd.123.sogou.com/u/2013/09/523a4b48292df.jpg");
		
		String loginResult = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(String.class, form);
		System.out.println(loginResult);
	}
	/**
	 * 查询微博绑定平台账号操作
	 */
	public void myUserByWeiboId(){
		
		String domin = "http://yaifeng.eicp.net/MicroBlogClientServer/rs/UserpluginService/getUserByWeiboId";

		
		Client client = new Client();
		
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		
		form.add("weibo","12345");
		form.add("icoPath","http://appd.123.sogou.com/u/2013/09/523a4b48292df.jpg");
		
		String loginResult = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(String.class, form);
		System.out.println(loginResult);
	}
	/**
	 * 检查登录账号是否存在并绑定
	 */
	public void myCheckAccount(){
		
		String domin = "http://yaifeng.eicp.net/MicroBlogClientServer/rs/UserpluginCheckAccountService/checkAccount";

		
		Client client = new Client();
		
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("account","mylogin@126.com");
		form.add("weibo","12345");
		form.add("icoPath","http://appd.123.sogou.com/u/2013/09/523a4b48292df.jpg");
		
		String loginResult = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(String.class, form);
		System.out.println(loginResult);
	}
	/**
	 * 更新或新增微博朋友数信息、微博二次转发数信息
	 */
	public void doFriendnumAndWeiboSecond(){
		
		String domin = "http://yaifeng.eicp.net/MicroBlogClientServer/rs/WeiboFriendnumService/doFriendnumAndWeiboSecond";

		Client client = new Client();
		
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("weibo","12345");
		form.add("userid","26431507");
		form.add("activityid","12345");
		form.add("friendnum","10");
		form.add("bowenid","12345");
		String loginResult = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(String.class, form);
		System.out.println(loginResult);
	}
	//本省活动
	public void activityLocalList(){
		String domin = "http://localhost:8080/MicroBlogClientServer/rs/ActivityLocalListService/activityLocalList";

		Client client = new Client();
		
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("deptid","001001001");
		
		String loginResult = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(String.class, form);
		System.out.println(loginResult);
	}
	/**
	 * 测试转发积分获取
	 */
	public void forwardService(){
		String domin = "http://localhost:8080/MicroBlogClientServer/rs/ForwardService/forwardService";

		Client client = new Client();
		
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("forwardType","1");
		form.add("user_id","97034612");
		form.add("activity_id","67d69550-c319-4284-9449-e200a47aa4ef");
		
		String loginResult = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(String.class, form);
		System.out.println(loginResult);
	}
	
	/**
	 * 最新活动
	 */
	@Test
	public void recentNewActivityList(){
		String domin = "http://localhost:8080/MicroBlogClientServer/rs/RecentNewActivityService/recentNewActivityList";

		Client client = new Client();
		
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("dept_id","001001");
		
		String loginResult = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(String.class, form);
		System.out.println(loginResult);
	}
}
