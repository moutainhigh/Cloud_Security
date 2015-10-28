package com.sinosoft.service.impl;

import java.io.IOException;
import java.io.Reader;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sinosoft.dao.CsAssetMapper;
import com.sinosoft.dao.CsOrderAssetMapper;
import com.sinosoft.util.JsonUtil;
import com.sinosoft.vo.CsAsset;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("OrderAssetService")
@Component
public class OrderAssetServiceImpl implements
		com.sinosoft.service.OrderAssetService {
	private final static SqlSessionFactory sqlSessionFactory;
	static {
		String resource = "SqlMapConfig.xml";
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	@POST
	@Path("{findAssetNameByOrderId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String findAssetNameByOrderId(@FormParam("orderId")
	String orderId) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CsOrderAssetMapper csOrderAssetMapper = sqlSession
				.getMapper(CsOrderAssetMapper.class);
		CsAsset csAsset = csOrderAssetMapper.findAssetNameByOrderId(orderId);
		return JsonUtil.encodeObject2Json(csAsset);
	}

}
