package test.ctbri.service;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class TestAlarm {

	@Test
	public void testFindAlarmByUserId() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/AlarmService/findAlarmByUserId";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("userId", 105);
		String alarm = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONArray jsonArray = JSONArray.fromObject(alarm);
		Assert.assertEquals("2265119720", jsonArray.getJSONObject(0).get("id"));
	}

	@Test
	public void testFindAlarm() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/AlarmService/countAlarm";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("userId", 96);
		String alarm = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONObject jsonObject = JSONObject.fromObject(alarm);
		Assert.assertEquals(17, jsonObject.get("count"));
	}

	@Test
	public void testFindAlarmByOrderId() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/AlarmService/findAlarmByOrderId";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("orderId", "15080711024680645");
		form.add("group_flag", "");
		form.add("type", 0);
		form.add("count", 10);
		form.add("level", 1);
		form.add("name", "CSRF跨站请求伪造漏洞");
		String asAlarm = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONArray jsonArray = JSONArray.fromObject(asAlarm);
		Assert.assertEquals(1065088, jsonArray.getJSONObject(0).get("id"));
	}
	@Test
	public void testFindAlarmByParams() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/AlarmService/findAlarm";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("websoc", 1);
		form.add("alarmId", 1065088);
		form.add("group_flag", "");
		form.add("type", 0);
		form.add("count", 10);
		form.add("level", 1);
		form.add("name", "CSRF跨站请求伪造漏洞");
		String asAlarm = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONArray jsonArray = JSONArray.fromObject(asAlarm);
		Assert.assertEquals(1065088, jsonArray.getJSONObject(0).get("id"));
	}
	@Test
	public void testFindAlarmByAlarmId() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/AlarmService/findAlarmByAlarmId";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("alarmId", 998335);
		String asAlarm = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONObject jsonObject = JSONObject.fromObject(asAlarm);
		Assert.assertEquals(998335, jsonObject.getInt("id"));
	}

}
