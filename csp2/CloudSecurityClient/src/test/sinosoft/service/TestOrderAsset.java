package test.sinosoft.service;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sinosoft.service.OrderAssetService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class TestOrderAsset {

	@Test
	public void testFindAsset() {
		String domin = "http://localhost:8080/CloudSecurityClient/rs/OrderAssetService/findAssetNameByOrderId";
		Client client = new Client();

		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("orderId", "15080518412335087");
		String csAsset = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONObject jsonObject = JSONObject.fromObject(csAsset);
		Assert.assertEquals("可用性1", jsonObject.get("name"));
	}
}
