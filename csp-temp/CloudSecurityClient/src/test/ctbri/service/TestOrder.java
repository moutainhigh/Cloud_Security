package test.ctbri.service;

import java.util.Date;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctbri.util.JsonMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class TestOrder {
	JsonMapper jsonMapper = new JsonMapper();
	@Test
	public void testFindOrder() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/OrderService/findOrderByUserIdAndState";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("userId", 96);
		form.add("state", "2");
		form.add("currentDate", new Date());
		form.add("pageNow", -1);
		form.add("pageSize", 10);
		String orderList = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONArray jsonArray = JSONArray.fromObject(orderList);
		System.out.println(jsonArray.getJSONObject(0));
		String id = jsonArray.getJSONObject(0).getString("id");
		Assert.assertEquals("15092516172565713", id);
	}

	@Test
	public void testFIndOrderMessage() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/OrderService/findOrderByOrderId";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("id", "15080518412335087");
		String csOrder = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONObject jsonObject = JSONObject.fromObject(csOrder);
		Assert.assertEquals("15080518412335087", jsonObject.get("id"));
	}

	@Test
	public void testCountOrder() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/OrderService/countOrder";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("userId", 96);
		String csOrder = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONObject index = JSONObject.fromObject(csOrder);
		Assert.assertEquals(30, index.get("count"));
	}
	@Test
	public void testServerCountOrder() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/OrderService/countServerOrder";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("userId", 96);
		form.add("state", 1);
		form.add("currentDate", new Date());
		String csOrder = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONObject index = JSONObject.fromObject(csOrder);
		Assert.assertEquals(2, index.get("count"));
	}
	@Test
	public void testAlarmCountOrder() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/AlarmService/countAlarm";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("userId", 96);
		String csOrder = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONObject index = JSONObject.fromObject(csOrder);
		Assert.assertEquals(17, index.get("count"));
	}
}
