package test.ctbri.service;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctbri.exception.CloudException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class TestAsset {
	@Test
	public void testAssetById(){
		String domin = "http://localhost:8080/CloudSecurityClient/rs/AssetService/selectByPrimaryKey";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("id", 21);
		String asset = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONObject jsonObject = JSONObject.fromObject(asset);
		Assert.assertEquals(21, jsonObject.get("id"));
	}
	@Test
	public void testAsset() throws CloudException{
		String domin = "http://localhost:8080/CloudSecurityClient/rs/AssetService/selectByUserId";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("userId", 105);
		form.add("pageNow", -1);
		form.add("pageSize", -1);
		System.out.println(form);
		String assetObject = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
		.post(String.class, form);
		JSONArray jsonArray = JSONArray.fromObject(assetObject);
		Assert.assertEquals(20, jsonArray.getJSONObject(0).get("id"));
	}
	
	@Test
	public void testAssetCount() throws CloudException{
		String domin = "http://localhost:8080/CloudSecurityClient/rs/AssetService/getCountByUserId";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("userId", 105);
		String assetObject = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(String.class, form);
		JSONObject jsonObject = JSONObject.fromObject(assetObject);
		Assert.assertEquals(5, jsonObject.get("count"));
	}
	
	@Test
	public void testAssetStatus() throws CloudException{
		String domin = "http://localhost:8080/CloudSecurityClient/rs/AssetService/selectByUserIdAndStatus";
		Client client = new Client();
		WebResource webResource = client.resource(domin);
		Form form = new Form();
		form.add("userId", 96);
		form.add("status", 1);
		form.add("pageNow", 1);
		form.add("pageSize", 3);
		String assetList = webResource.accept(MediaType.APPLICATION_JSON_TYPE)
		.post(String.class, form);
		JSONArray jsonArray = JSONArray.fromObject(assetList);
		Assert.assertEquals(35, jsonArray.getJSONObject(0).get("id"));
	}
}
