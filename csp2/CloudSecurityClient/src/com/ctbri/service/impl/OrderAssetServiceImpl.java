package com.ctbri.service.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ctbri.dao.CsOrderAssetMapper;
import com.ctbri.util.JsonUtil;
import com.ctbri.vo.CsAsset;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("OrderAssetService")
@Component
public class OrderAssetServiceImpl extends ServiceCommon implements
		com.ctbri.service.OrderAssetService {

	@POST
	@Path("{findAssetNameByOrderId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findAssetNameByOrderId(@FormParam("orderId")
	String orderId) {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		CsOrderAssetMapper csOrderAssetMapper = sqlSession
				.getMapper(CsOrderAssetMapper.class);
		CsAsset csAsset = csOrderAssetMapper.findAssetNameByOrderId(orderId);
		return JsonUtil.encodeObject2Json(csAsset);
	}

}
