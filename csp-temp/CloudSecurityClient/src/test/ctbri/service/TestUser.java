package test.ctbri.service;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class TestUser {
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
