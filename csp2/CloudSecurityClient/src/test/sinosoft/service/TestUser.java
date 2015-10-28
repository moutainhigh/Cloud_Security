package test.sinosoft.service;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sinosoft.exception.CloudException;
import com.sinosoft.service.UserLoginService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class TestUser {
	public Object getBeans(String object) {
		ApplicationContext ctx = null;
		ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		return ctx.getBean(object);
	}

	@Test
	public void testUser() throws CloudException {
		UserLoginService u = (UserLoginService) this
				.getBeans("userLoginService");
		String user = u.login("test31", "123456");
		JSONObject jsonObject = new JSONObject();
		jsonObject = jsonObject.fromObject(user);
		Assert.assertNotNull("用户登陆校验", user);
		Assert.assertEquals("10001", jsonObject.get("state"));
	}

	@Test
	public void testUserLogin() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/LoginService/login";
		Client client = new Client();

		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("account", "test321");
		form.add("password", "123456");
		String user = webResource.accept(MediaType.APPLICATION_JSON_TYPE).post(
				String.class, form);
		JSONObject jsonObject = new JSONObject();
		jsonObject = jsonObject.fromObject(user);
		Assert.assertNotNull("用户登陆校验", user);
		Assert.assertEquals("10001", jsonObject.get("state"));
	}
}
